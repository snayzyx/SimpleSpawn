# SimplesSpawn Plugin

A lightweight and fully customizable Spawn Plugin for Minecraft Spigot servers (1.21+). Easily manage player spawn points with sound effects, configurable delays, and comprehensive customization.

## 🌟 Features

- **Easy Spawn Management** - Set and manage spawn points with simple commands
- **Customizable Teleportation** - Configure delay, cooldown, and cancel-on-move behavior
- **Sound Effects** - Custom sounds on teleportation and countdown (each second)
- **Smart Coordinate Rounding** - Automatically round to block centers
- **First Join Teleportation** - Optional teleport for new players
- **Flexible Messages** - Fully customizable with color codes
- **Auto Backup** - Automatic config backups on reload

## 📋 Commands

- `/spawn` - Teleport to spawn (Permission: `spawn.use`)
- `/spawn set` - Set spawn location (Permission: `spawn.set`)
- `/spawn reload` - Reload config (Permission: `spawn.reload`)

## ⚙️ Configuration

All settings are customizable in `config.yml`:

```yaml
teleportation:
  delay: 3              # Seconds before teleport
  cancel-on-move: true  # Cancel if player moves
  cooldown: 10          # Seconds between teleports
  play-sound: true
  play-countdown-sound: true
```

Messages support color codes (`&a`, `&c`, etc.) and placeholders like `{time}`.

## 🔊 Sounds

Teleportation: `ENTITY_ENDERMAN_TELEPORT`, `ENTITY_PLAYER_TELEPORT`
Countdown: `BLOCK_NOTE_BLOCK_PLING`, `BLOCK_NOTE_BLOCK_HAT`

## 📥 Installation

1. Download `SimplesSpawn-1.0-SNAPSHOT.jar`
2. Place in `plugins/` folder
3. Restart server
4. Edit `plugins/SimplesSpawn/config.yml` as needed

## 📄 License

MIT License - See GitHub repository for details

**GitHub**: https://github.com/snayzyx/SimpleSpawn

