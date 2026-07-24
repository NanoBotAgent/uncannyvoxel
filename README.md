# The Uncanny Voxel

A psychological horror Fabric mod for Minecraft Java Edition 26.2 "Chaos Cubed".

## Features

### Core Horror Systems
- **Vantablack Chunks**: Lightmaps dropped in chunks near Sulfur Geysers
- **Friend List Corruption**: Client-side name corruption and fake presence
- **Blink Shader**: 0.5s pitch blackness with wet tearing sound (photosensitivity safe mode)
- **Peripheral Glitch**: Chromatic aberration in screen corners when entities are nearby but not looked at
- **Depth of Field Dread**: Crosshair blur when entity is directly behind player

### The Substrate Dimension
- **Registry Key**: `uncannyvoxel:substrate`
- Lightless, no skylight, ambient light 0
- Ceiling at ~10 blocks with hyper-realistic concrete texture
- Custom blocks: Compacted Hair, Rusted Grate
- Monster spawn light level 0

### Portal: Sulfur-Glass Mirror
- 3x3 horizontal frame of Tinted Glass and/or Sulfur Cubes
- Center: Sulfur-Glass Mirror block
- Activated by dropping Desaturated Eye on mirror
- No swirling portal - shattered mirror effect, player falls through

### Custom Entities
- **Mimic** (`uncannyvoxel:mimic`): Inverted joints, sliding skin on damage, stutter-step pathfinding

### Items
- **Desaturated Eye**: Portal activation item
- **Lumen Scalpel**: Reveals invisible entities, drains max health (safe floor)
- **Tether Stake**: 5-block safe radius, geometry cannot shift inside

### Structures
- **Non-Euclidean Flesh-Farms**: Brutalist slaughterhouse maze, interior shifts when not observed
- **Weeping Monoliths**: Server-rack towers of pulsing meat and fiber optics

## Installation

1. Install Fabric Loader 0.16.0+ for Minecraft 26.2
2. Install Fabric API 0.100.0+26.2
3. Place `uncannyvoxel-1.0.0.jar` in `mods/` folder

## Configuration

Config file: `config/uncannyvoxel.json`

Key options:
- `masterHorrorEnabled`: Toggle all horror features
- `photosensitivitySafeMode`: Enabled by default - reduces blink frequency and intensity
- `vantablackChunksEnabled`: Toggle Vantablack chunk rendering
- `friendListCorruptionEnabled`: Toggle friend name corruption
- `peripheralGlitchEnabled`: Toggle peripheral vision effects
- `blinkEnabled`: Toggle blink shader
- `infrasonicDroneEnabled`: Toggle low-frequency ambient sound
- `mimicVoiceEnabled`: Disabled by default - requires explicit consent
- `bedParalysisEnabled`: Toggle bed paralysis scenario
- `lumenScalpelMaxHealthDrain`: Maximum health drain (0.0-1.0)
- `tetherStakeRadius`: Safe zone radius in blocks

## Accessibility

- **Photosensitivity Safe Mode**: Enabled by default. Blink duration max 0.5s, cooldown min 60s (safe mode) vs 25s (normal)
- **Audio Volume Clamping**: All horror sounds clamped to safe levels
- **Infrasonic Frequency Floor**: Minimum 32 Hz
- **Microphone Consent**: Disabled by default, requires explicit opt-in with versioned consent
- **Friend Name Anonymization**: Option to replace real names with fictional placeholders
- **Emergency Cancel**: Bed paralysis can be cancelled with Escape key

## Safety

- All horror effects are client-side visual/audio only
- No network transmission of friend data
- No persistent microphone recording
- Server-safe: no client-only classes loaded on dedicated server
- Configurable safe floors for health drain mechanics

## Building

```bash
./gradlew build
```

Output: `build/libs/uncannyvoxel-1.0.0.jar`

## Testing

```bash
# Unit tests
./gradlew test

# GameTests (requires client)
./gradlew runGametest

# Full CI verification
./ci/verify.sh
```

## Compatibility

- Minecraft: 26.2
- Fabric Loader: 0.16.0+
- Fabric API: 0.100.0+26.2
- Java: 21
- Yarn Mappings: 26.2+build.1:v2

## License

All Rights Reserved.

## Credits

- Built with Fabric Mod Loader
- Psychological horror design inspired by analog horror and uncanny valley research