# Accessibility Specification

## Mandatory Requirements

All horror systems must comply with the following accessibility standards:

### Photosensitivity Safety

| Parameter | Normal Mode | Safe Mode (Default) |
|-----------|-------------|---------------------|
| Blink Duration | ≤0.5s (10 ticks) | ≤0.5s (10 ticks) |
| Blink Min Cooldown | 25s (500 ticks) | 60s (1200 ticks) |
| Blink Max Cooldown | 180s (3600 ticks) | 180s (3600 ticks) |
| Peripheral Glitch Intensity | 0.0-1.0 | 0.0-0.5 |
| Depth of Field Dread | Full | Reduced |

**Implementation**:
- `HorrorConfig.photosensitivitySafeMode = true` by default
- BlinkScheduler enforces safe mode cooldowns
- UncannyRenderBridge clamps intensities in safe mode
- Config option to disable safe mode for players who want full intensity

### Audio Safety

| Parameter | Limit |
|-----------|-------|
| Master Horror Volume | ≤0.5 (configurable 0.0-1.0) |
| Infrasonic Drone Volume | ≤0.2 |
| Infrasonic Frequency Floor | ≥32 Hz |
| Binaural Whispers Volume | ≤0.3 |
| Corrupted Familiarity Layer | ≤0.25 |

**Implementation**:
- `AudioSafety` class clamps all values at runtime
- Config sliders cannot exceed safe maximums
- Infrasonic generator enforces 32 Hz minimum

### Microphone Horror (Opt-in Only)

| Requirement | Implementation |
|-------------|----------------|
| Disabled by default | `mimicVoiceEnabled = false` |
| Explicit consent required | `MicPolicy.grantConsent(version)` |
| Versioned consent | `micConsentVersion` in config |
| Local-only processing | No network transmission |
| No persistent storage | Buffer only, cleared on disconnect |
| Distorted delayed playback | ≥500ms delay, pitch shift |
| Safe failure | Graceful degradation if permission denied |
| Revocable at any time | `MicPolicy.revokeConsent()` |

### Visual Horror Toggles

All visual horror effects have individual toggles:

| Effect | Config Key | Default |
|--------|------------|---------|
| Vantablack Chunks | `vantablackChunksEnabled` | true |
| Friend Name Corruption | `friendListCorruptionEnabled` | true |
| Peripheral Glitch | `peripheralGlitchEnabled` | true |
| Blink Shader | `blinkEnabled` | true |
| Depth of Field Dread | `depthOfFieldDreadEnabled` | true |
| Inventory Rot Eye | `inventoryRotEnabled` | true |

### Friend List Privacy

| Feature | Implementation |
|---------|----------------|
| Client-side only | No network calls for friend data |
| No logging of real names | NameCorruptor never logs |
| Fictional placeholder option | `useFictionalFriendNames = true` |
| Configurable corruption intensity | `maxCorruptionIntensity = 0.0-1.0` |

### Bed Paralysis Safety

| Safety Feature | Implementation |
|----------------|----------------|
| Emergency cancel key | `bedParalysisCancelKey = "key.escape"` |
| Max duration limit | `bedParalysisMaxDurationMinutes = 10` |
| Accessibility skip | `bedParalysisEnabled = false` in config |
| No direct damage | Only visual/audio, no health loss |
| No soft-lock | Always cancellable |

### Lumen Scalpel Health Drain

| Safety Feature | Implementation |
|----------------|----------------|
| Max drain configurable | `lumenScalpelMaxHealthDrain = 0.5` (50%) |
| Safe floor | Never below 50% of max health (2 hearts min) |
| Automatic restoration | Modifier removed when item deselected |
| Visual indicator | Tooltip warns about health drain |

### Tether Stake Safe Zone

| Safety Feature | Implementation |
|----------------|----------------|
| Geometry stability | `DreadModel.registerSafeZone()` prevents shifts |
| Hostile exclusion | Entities cannot path into zone |
| Violent uproot warning | Sound + alert before stake breaks |
| Radius configurable | `tetherStakeRadius = 5` blocks |

## Config Schema

```json
{
  "masterHorrorEnabled": true,
  "photosensitivitySafeMode": true,
  "horrorVolume": 0.35,
  "infrasonicVolume": 0.15,
  "infrasonicFrequencyHz": 32,
  "vantablackChunksEnabled": true,
  "vantablackRangeChunks": 3,
  "friendListCorruptionEnabled": true,
  "useFictionalFriendNames": false,
  "maxCorruptionIntensity": 0.3,
  "peripheralGlitchEnabled": true,
  "peripheralGlitchIntensity": 0.3,
  "blinkEnabled": true,
  "blinkMinCooldownTicks": 1200,
  "blinkMaxCooldownTicks": 3600,
  "blinkDurationTicks": 10,
  "depthOfFieldDreadEnabled": true,
  "infrasonicDroneEnabled": true,
  "binauralWhispersEnabled": true,
  "binauralWhispersVolume": 0.2,
  "corruptedFamiliarityEnabled": true,
  "mimicVoiceEnabled": false,
  "micCaptureEnabled": false,
  "micConsentVersion": 1,
  "inventoryRotEnabled": true,
  "mirrorTrapEnabled": true,
  "chestMimicEnabled": true,
  "bedParalysisEnabled": true,
  "bedParalysisMaxDurationMinutes": 10,
  "bedParalysisCancelKey": "key.escape",
  "lumenScalpelMaxHealthDrain": 0.5,
  "tetherStakeRadius": 5
}
```

## Testing Accessibility

Run accessibility validation:
```bash
./gradlew test --tests "*Accessibility*" --no-daemon
```

Key test assertions:
- All volumes clamp to safe maximums
- Safe mode doubles blink cooldown
- Infrasonic floor at 32 Hz
- Mic consent required and versioned
- Emergency cancel always works
- Safe zones prevent geometry shifts
- Health drain has safe floor