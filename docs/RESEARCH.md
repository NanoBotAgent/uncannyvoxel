# Research Documentation

## Minecraft 26.2 "Chaos Cubed" - Verification-Gated Dossier

> **IMPORTANT**: This document contains version-gated assumptions. All 26.2-specific internals must be verified against actual 26.2 sources before release.

## Assumed / Claimed 26.2 Features

| Feature | Assumed Behavior | Mod Hook Strategy | Verification Status |
|---------|-----------------|-------------------|---------------------|
| Version numbering | Minecraft Java uses 26.2 as year-based version | Fabric Loom: `minecraft "com.mojang:minecraft:26.2"` | ❌ UNVERIFIED |
| Vulkan renderer | Experimental Vulkan backend replaces/augments GL | Mixin into Vulkan renderer classes / Fabric Renderer API | ❌ UNVERIFIED |
| Friend-list UI | New social/friend-list UI with presence | Client-side Mixins into friend-list widgets | ❌ UNVERIFIED |
| Sulfur blocks/geysers | New Sulfur Cube, Sulfur Geyser blocks | Tags: `#uncannyvoxel:sulfur_blocks`, `#uncannyvoxel:sulfur_geysers` | ❌ UNVERIFIED |
| Dimension/biome APIs | Data-driven dimensions use JSON registries | Register `uncannyvoxel:substrate` as RegistryKey<World> | ⚠️ PARTIAL |

## Research Checklist for Browsing Agent

### 1. Minecraft 26.2 Version String
- [ ] Fabric Loom compatible artifact name
- [ ] Java version requirement
- [ ] Fabric Loader/Fabric API compatible versions
- [ ] Yarn mappings availability

### 2. Vulkan Renderer Internals
- [ ] Does 26.2 expose a Fabric API for Vulkan?
- [ ] Are lightmaps still texture-based or descriptor-set based?
- [ ] What methods can be safely Mixined without breaking render thread safety?
- [ ] Actual class names: `VulkanLightmapTextureManager`, `VulkanChunkRenderBackend`, `VulkanPostProcessPass`

### 3. Friend-List UI
- [ ] Exact screen/widget classes (`FriendListScreen`, `SocialInteractionsScreen`, `FriendListWidget`, `FriendEntry`)
- [ ] Whether friend entries are Entry widgets, list entries, or data-driven UI
- [ ] Whether names are Text, String, or GameProfile
- [ ] Presence data structure

### 4. Sulfur Content
- [ ] Exact block IDs (`minecraft:sulfur_cube`, `minecraft:sulfur_geyser`, etc.)
- [ ] Whether Sulfur Geysers are block entities, scheduled tick blocks, or particle emitters
- [ ] Existing tags: `minecraft:sulfur_blocks`

### 5. Dimension JSON Schema
- [ ] `dimension_type` fields in 26.2
- [ ] Noise/flat generator schema
- [ ] Monster spawn light level fields

### 6. Audio Engine
- [ ] Whether Minecraft 26.2 still uses OpenAL/SoundManager
- [ ] Whether custom AudioStream can be used for procedural infrasound
- [ ] Whether HRTF/spatialization is exposed

## Fabric API / Loom Versions for 26.2

| Component | Expected Version | Status |
|-----------|-----------------|--------|
| Fabric Loader | 0.16.0+ | ❌ UNVERIFIED |
| Fabric API | 0.100.0+26.2 | ❌ UNVERIFIED |
| Yarn Mappings | 26.2+build.1:v2 | ❌ UNVERIFIED |
| Loom | 1.7-SNAPSHOT | ❌ UNVERIFIED |

## Version-Gated Implementation Strategy

1. **Interface Adapters**: All 26.2-specific hooks behind interfaces with runtime implementation selection
2. **Mixin `defaultRequire = 0`**: Vulkan/Friend-list Mixins default to no-op until verified
3. **Feature Flags**: Each 26.2 feature toggleable in config
4. **Graceful Degradation**: Missing classes = feature disabled, no crashes

## Known Risks

- **Yarn mappings may not exist** for 26.2 yet → need custom intermediary mappings
- **Vulkan classes may be internal-only** → Mixins may need obfuscated names
- **Friend-list rewrite in 26.x** → widget hierarchy likely changed from 1.21.x
- **Sulfur blocks may not exist** in 26.2 → fallback to custom block registration