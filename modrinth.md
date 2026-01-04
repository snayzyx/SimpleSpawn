# SimplesSpawn Plugin

A lightweight and fully customizable Spawn Plugin for Minecraft Spigot servers. Easily manage player spawn points with sound effects, configurable delays, and comprehensive customization options.

## 🌟 Features

- **Easy Spawn Management** - Set and manage spawn points with a simple command
- **Customizable Teleportation** - Configure delay, cooldown, and cancel-on-move behavior
- **Sound Effects** - Custom sounds on successful teleportation and countdown sounds for each second
- **Smart Coordinate Rounding** - Automatically round coordinates to block centers for precise spawn placement
- **First Join Teleportation** - Option to teleport new players to spawn on first join
- **Flexible Messages** - All player messages are fully customizable with color codes
- **Auto Backup** - Automatic configuration backups on reload
- **Placeholder Support** - Dynamic message placeholders like `{time}` for countdowns and cooldowns

## 📋 Commands

### `/spawn`
Teleports the player to the spawn point with a configurable delay.
- **Permission**: `spawn.use` (default: everyone)

### `/spawn set`
Sets the spawn point to the player's current location. Coordinates are automatically rounded to the block center.
- **Permission**: `spawn.set` (default: operators only)

### `/spawn reload`
Reloads the plugin configuration and creates a backup of the previous config.
- **Permission**: `spawn.reload` (default: operators only)

## ⚙️ Configuration

The plugin is fully customizable through the `config.yml` file located in `plugins/SimplesSpawn/`.

### Spawn Location
```yaml
spawn:
  world: world
  x: 0.5
  y: 64.0
  z: 0.5
  yaw: 0.0
  pitch: 0.0
```

### Customizable Messages
All messages support Minecraft color codes using the `&` character and include placeholder support:

```yaml
messages:
  teleport-success: '&aTeleported to spawn successfully!'
  teleport-countdown: '&eYou will be teleported in &6{time} &eseconds...'
  cooldown-active: '&cYou must wait &6{time} &cseconds before teleporting again!'
  spawn-set: '&aSpawn set at your current location!'
```

### Teleportation Settings
```yaml
teleportation:
  delay: 3                          # Seconds before teleportation
  cancel-on-move: true              # Cancel if player moves during delay
  cooldown: 10                       # Seconds between teleports
  teleport-on-first-join: true      # Teleport new players on first join
  teleport-on-join: false           # Teleport all players on every join
  play-sound: true                  # Enable teleportation sound
  sound-type: ENTITY_ENDERMAN_TELEPORT
  play-countdown-sound: true        # Enable countdown sounds
  countdown-sound-type: BLOCK_NOTE_BLOCK_PLING
```

### General Settings
```yaml
settings:
  show-coordinates: true            # Show coordinates when setting spawn
  auto-backup: true                 # Auto backup on config reload
  round-coordinates: true           # Round to block center
  round-to-block-center: true
```

## 🔊 Sound Types

**Teleportation Sounds:**
- `ENTITY_ENDERMAN_TELEPORT` (default)
- `ENTITY_PLAYER_TELEPORT`
- `ENTITY_ENDER_PEARL`

**Countdown Sounds:**
- `BLOCK_NOTE_BLOCK_PLING` (default)
- `BLOCK_NOTE_BLOCK_HAT`
- `BLOCK_NOTE_BLOCK_BELL`
- `UI_BUTTON_CLICK`

## 🔐 Permissions

| Permission | Description | Default |
|-----------|-------------|---------|
| `spawn.use` | Allow teleporting to spawn | Everyone |
| `spawn.set` | Allow setting spawn location | Operators |
| `spawn.reload` | Allow reloading configuration | Operators |

## 📝 Example Configuration

```yaml
spawn:
  world: world
  x: 100.5
  y: 64.0
  z: 200.5

messages:
  teleport-success: '&a✓ &fYou have been teleported to spawn!'
  teleport-countdown: '&eTeleporting in &6{time}s&e...'
  cooldown-active: '&cYou must wait &6{time}s &cbefore teleporting again!'
  spawn-set: '&a✓ &fSpawn set at your location!'

teleportation:
  delay: 5
  cancel-on-move: true
  cooldown: 30
  teleport-on-first-join: true
  play-sound: true
  sound-type: ENTITY_ENDERMAN_TELEPORT
  play-countdown-sound: true
  countdown-sound-type: BLOCK_NOTE_BLOCK_PLING

settings:
  show-coordinates: true
  auto-backup: true
  round-coordinates: true
  round-to-block-center: true
```

## 🚀 Installation

1. Download `SimplesSpawn-1.0-SNAPSHOT.jar` from the releases
2. Place it in your `plugins/` folder
3. Restart your server
4. The plugin will generate a default `config.yml` in `plugins/SimplesSpawn/`
5. Customize the configuration as needed
6. Use `/spawn reload` to reload the configuration

## 💡 Usage Tips

### Custom Welcome Message
```yaml
teleport-countdown: '&b[&fSpawn&b] &7In &6{time}s &7you will go to spawn...'
```

### Disable Sounds
```yaml
play-sound: false
play-countdown-sound: false
```

### Longer Teleportation Delay
```yaml
delay: 10  # 10 seconds
```

### Remove Cooldown
```yaml
cooldown: 0
```

## 🔧 Troubleshooting

**Players Can't Teleport**
- Check if they have the `spawn.use` permission
- Verify the spawn world exists
- Ensure the spawn location is set (run `/spawn set`)

**Invalid Sound Warnings**
- Ensure sound names are correct (see Sound Types section)
- Use uppercase sound names with underscores

**Coordinates Not Rounding Correctly**
- Set `round-coordinates: true`
- Set `round-to-block-center: true` for center alignment

## 📊 Requirements

- **Minecraft Version**: 1.21+
- **Server Software**: Spigot / Paper
- **Java**: Java 17+

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 🤝 Contributing

Contributions are welcome! Feel free to open issues or pull requests on the GitHub repository.

## 💬 Support

For issues or questions:
- Check the documentation on GitHub
- Open an issue on the GitHub repository
- Read the CONTRIBUTING.md file for development guidelines

---

**Made with ❤️ by skymord**

**GitHub**: https://github.com/snayzyx/SimpleSpawn
**Version**: 1.0.0
**Last Updated**: January 2026

