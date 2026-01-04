# Changelog

All notable changes to the SimplesSpawn Plugin will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.0.0] - 2025-01-04

### Added
- Initial release of SimplesSpawn Plugin
- Basic spawn teleportation command (`/spawn`)
- Spawn location setting (`/spawn set`)
- Configuration reload command (`/spawn reload`)
- Customizable teleportation delay with countdown
- Configurable cooldown system
- Sound effects for teleportation
- Countdown sounds for each second of delay
- First join player teleportation
- Smart coordinate rounding to block center
- Fully customizable messages with color codes
- Message placeholders support (`{time}`)
- Auto-backup configuration on reload
- Comprehensive permission system
- Comprehensive configuration file
- Full Minecraft 1.21 support

### Features
- **Teleportation System**: Players can teleport to spawn with configurable delay
- **Sound Effects**: Custom sounds on teleportation and during countdown
- **Coordinate Management**: Automatic rounding to block center for precise spawns
- **Message Customization**: All messages are configurable with color code support
- **Smart Configuration**: Well-documented config with helpful comments
- **Auto Backup**: Automatic backups on configuration reload
- **Permission Support**: Granular permission control for all commands

### Config Options
- `delay`: Teleportation delay in seconds
- `cancel-on-move`: Cancel teleportation if player moves
- `cooldown`: Cooldown between teleportations
- `teleport-on-first-join`: Teleport new players on first join
- `teleport-on-join`: Teleport all players on join
- `play-sound`: Enable/disable teleportation sounds
- `sound-type`: Custom sound for teleportation
- `play-countdown-sound`: Enable/disable countdown sounds
- `countdown-sound-type`: Custom sound for countdown
- `show-coordinates`: Show coordinates when setting spawn
- `auto-backup`: Create backups on reload
- `round-coordinates`: Round coordinates when setting spawn
- `round-to-block-center`: Round to block center (0.5 offset)

### Commands
- `/spawn` - Teleport to spawn
- `/spawn set` - Set spawn location at current position
- `/spawn reload` - Reload configuration

### Permissions
- `spawn.use` - Allow teleporting to spawn (default: everyone)
- `spawn.set` - Allow setting spawn (default: operators)
- `spawn.reload` - Allow reloading config (default: operators)

---

## Planned Features (Future Releases)

- [ ] Multiple spawn points per world
- [ ] Per-group spawn locations
- [ ] Spawn particle effects
- [ ] Economy integration for teleportation costs
- [ ] Spawn area protection
- [ ] Advanced GUI for configuration
- [ ] API for other plugins

---

**Maintainer**: skymord

