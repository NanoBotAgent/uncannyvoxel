# Progression Design

## Overview

The Uncanny Voxel mod follows a subtle, discovery-based progression. There are no explicit quests or guides. Players uncover horror systems through environmental storytelling and experimentation.

## Tier 1: Surface Unease (Overworld)

### Triggers
- Nighttime in areas with Sulfur Cubes nearby
- Looking at reflections in Sulfur-Glass Mirrors
- Opening Chests that are actually Mimics

### Effects
- Occasional name corruption in friend list/tab list
- Peripheral vision glitches when entities nearby
- Infrasound drone in caves near sulfur
- Inventory background occasionally shows eye

### Player Actions
- Craft Desaturated Eye from sulfur + eye of ender
- Build 3x3 Sulfur-Glass Mirror frame with Tinted Glass
- Drop Eye on mirror to activate portal

## Tier 2: The Substrate (Dimension)

### Entry
- Activated Sulfur-Glass Mirror portal
- Player falls through shattered mirror into Substrate
- No return portal - must find Tether Stake or die

### Substrate Environment
- **Lightless**: No sky light, ambient light 0
- **Ceiling**: Hyper-realistic concrete ~10 blocks up
- **Blocks**: Compacted Hair (floor), Rusted Grate (walls/ceilings)
- **Spawn**: Safe platform generated at (0, y, 0)

### Hazards
- **Vantablack Chunks**: Lightmaps dropped near Sulfur Geysers
- **Non-Euclidean Flesh-Farms**: Maze structures that shift when unobserved
- **Weeping Monoliths**: Meat towers attracting entities
- **Mimic Entities**: Humanoid horrors with inverted joints

### Tools
- **Lumen Scalpel**: Cuts through fog, reveals invisible entities, drains max health
- **Tether Stake**: Creates 5-block safe zone (geometry stable, no hostile entry)

## Tier 3: Deep Horror Systems

### Mirror Trap
- Look into mirror in darkness (light level 0)
- Reflection stops mimicking, stares, mouths "Don't turn around"
- Optional: Darkness effect applied

### Chest Mimic
- Interact with mimic chest
- Jaw unhinges, pulsing throat revealed
- 5 hearts damage + wet snap sound + "It tasted you." message

### Bed Paralysis
- Sleep in Substrate or near horror source
- Camera locked in first-person lying view
- 10 real-time minutes: shadows elongate, approach
- Emergency cancel key (Escape by default)
- Accessibility: skippable in config

### Microphone Horror (Opt-in)
- Disabled by default
- Requires explicit consent + version acceptance
- Local-only processing, no upload
- Distorted delayed playback
- Safe failure if permission denied

## Accessibility Integration

All horror systems respect:
- `photosensitivitySafeMode` (enabled by default)
- Master `horrorEnabled` toggle
- Per-system toggles
- Volume clamping
- Emergency cancels

## Completion

No "ending" - the mod is an atmosphere/experience. Players choose how deep to go.