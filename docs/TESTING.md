# Testing Guide

## Test Suite Overview

The Uncanny Voxel mod includes three layers of testing:

1. **Unit Tests** (JUnit 5) - Pure logic, no Minecraft runtime required
2. **Fabric GameTests** - In-game integration tests running on Fabric server
3. **CI Verification** - Automated validation gates

## Running Tests Locally

### Unit Tests
```bash
./gradlew test --no-daemon
```

### GameTests (Requires Minecraft client)
```bash
# Run all GameTests
./gradlew runGametest --no-daemon

# Run specific test class
./gradlew runGametest --tests com.uncannyvoxel.test.gametest.UncannyPortalGameTest --no-daemon
```

### Full CI Validation
```bash
./gradlew clean build test runGametest validateResources --no-daemon
```

## Test Categories

### Unit Tests (`src/test/java/com/uncannyvoxel/test/`)

| Test Class | Coverage |
|------------|----------|
| `PortalFrameValidatorTest` | Frame validation logic (valid, missing ring, invalid center) |
| `ActivationDecisionTest` | Portal activation decision matrix |
| `AudioSafetyTest` | Volume/frequency clamping, dread normalization |
| `BlinkSchedulerTest` | Cooldown enforcement, strobe prevention, determinism |
| `MicPolicyTest` | Default disabled, consent requirements, version checking |
| `NameCorruptorTest` | Deterministic corruption, null handling, blank names |
| `TetherStakeZoneTest` | Radius inclusion/exclusion, null tether handling |
| `DreadModelTest` | 0-1 range, safe zones, entity proximity, NaN prevention |

### GameTests (`src/test/java/com/uncannyvoxel/test/gametest/`)

| Test Class | Coverage |
|------------|----------|
| `UncannyPortalGameTest` | Portal frame validation, invalid frame rejection |
| `UncannyDimensionGameTest` | Substrate dimension registration, chunk generation stability |

## CI Pipeline

The CI workflow (`.github/workflows/ci.yml`) runs:

1. **Build and Test Job** - Compiles, runs unit tests, runs GameTests, validates resources
2. **CI Verification Gate** - Fails if build-and-test job didn't succeed
3. **Lint and Validate** - Gradle wrapper validation

### CI Verification Gate

The final gate job named **"CI verification gate"** must pass for the overall CI to succeed. It checks that the `build-and-test` job completed successfully.

## Test Development Guidelines

### Adding Unit Tests
1. Create test in `src/test/java/com/uncannyvoxel/test/`
2. Use JUnit 5 + Mockito
3. Mock `BlockView`, `World`, etc. with Mockito
4. Test pure logic only - no Minecraft runtime dependencies

### Adding GameTests
1. Implement `FabricGameTest` interface
2. Use `@GameTest(templateName = FabricGameTest.EMPTY_STRUCTURE)`
3. Use `TestContext` for assertions
4. Call `context.complete()` when done

### Test Naming
- Unit tests: `*Test.java` suffix
- GameTests: `*GameTest.java` suffix
- Test methods: descriptive camelCase names

## Mocking Patterns

```java
// Mock BlockView for PortalFrameValidator
BlockView world = Mockito.mock(BlockView.class);
Mockito.when(world.getBlockState(pos)).thenReturn(blockState);

// Mock World for dimension tests
ServerWorld world = Mockito.mock(ServerWorld.class);
Mockito.when(world.getServer()).thenReturn(server);
Mockito.when(server.getWorld(ModDimensions.SUBSTRATE)).thenReturn(substrateWorld);
```

## Common Issues

| Issue | Solution |
|-------|----------|
| GameTests hang | Ensure `context.complete()` is called |
| Unit test Mockito errors | Use `Mockito.mock()` not `Mockito.spy()` for interfaces |
| Gradle daemon OOM | Use `--no-daemon` flag |
| Yarn mappings missing | Run with `--refresh-dependencies` |

## Coverage Goals

- Unit test coverage: ≥80% for pure logic modules
- GameTest coverage: All portal/dimension functionality
- CI gate: Must pass on all PRs and main branch pushes