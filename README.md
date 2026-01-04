# SimpleSpawn Plugin

A lightweight and fully customizable Spawn Plugin for Minecraft Spigot servers. Easily manage player spawn points with sound effects, configurable delays, and comprehensive customization options.

## Features ✨

- **Easy Spawn Management**: Set and manage spawn points with a simple command
- **Customizable Teleportation**: Configure delay, cooldown, and cancel-on-move behavior
- **Sound Effects**: 
  - Custom sounds on successful teleportation
  - Countdown sounds for each second of delay
  - All sounds are fully configurable
- **Smart Coordinate Rounding**: Automatically round coordinates to block centers for precise spawn placement
- **First Join Teleportation**: Option to teleport new players to spawn on first join
- **Flexible Messages**: All player messages are fully customizable with color codes
- **Auto Backup**: Automatic configuration backups on reload
- **Placeholder Support**: Dynamic message placeholders like `{time}` for countdowns and cooldowns

## Installation 🚀

1. Download the latest release from the [Releases](https://github.com/skymord/Spawn/releases) page
2. Place `Spawn-1.0-SNAPSHOT.jar` in your server's `plugins` folder
3. Restart your server
4. The plugin will generate a default `config.yml` file in the `plugins/simplesSpawn` directory
5. Customize the configuration file as needed (see [Configuration](#configuration) section)
6. Use `/spawn reload` to reload the configuration

## Commands 📋

### `/spawn`
Teleports the player to the spawn point with a configurable delay.

**Permissions**: `spawn.use` (default: everyone)

### `/spawn set`
Sets the spawn point to the player's current location. Coordinates are automatically rounded to the block center.

**Permissions**: `spawn.set` (default: operators only)

### `/spawn reload`
Reloads the plugin configuration and creates a backup of the previous config.

**Permissions**: `spawn.reload` (default: operators only)

## Configuration ⚙️

The plugin is fully customizable through the `config.yml` file located in `plugins/SimplesSpawn/`.

### Spawn Location
```yaml
spawn:
  world: world                 # World name where spawn is located
  x: 0.5                       # X coordinate
  y: 64.0                      # Y coordinate
  z: 0.5                       # Z coordinate
  yaw: 0.0                     # Player rotation (yaw)
  pitch: 0.0                   # Player rotation (pitch)
```

### Messages
All messages support **Minecraft color codes** using the `&` character:
- `&a` = Green
- `&c` = Red
- `&e` = Yellow
- `&7` = Gray
- etc.

Available **placeholders**:
- `{time}` - Displays remaining seconds (used in countdown and cooldown messages)

Example:
```yaml
messages:
  teleport-success: '&aTeleported to spawn successfully!'
  teleport-countdown: '&eYou will be teleported in &6{time} &eseconds...'
  cooldown-active: '&cYou must wait &6{time} &cseconds before teleporting again!'
```

### Teleportation Settings
```yaml
teleportation:
  delay: 3                          # Delay before teleportation (seconds)
  cancel-on-move: true              # Cancel if player moves during delay
  cooldown: 10                       # Cooldown between teleportations (seconds)
  teleport-on-first-join: true      # Teleport new players on first join
  teleport-on-join: false           # Teleport all players on every join
  play-sound: true                  # Enable teleportation sound
  sound-type: ENTITY_ENDERMAN_TELEPORT  # Sound type for teleportation
  play-countdown-sound: true        # Enable countdown sounds
  countdown-sound-type: BLOCK_NOTE_BLOCK_PLING  # Sound type for countdown
```

### General Settings
```yaml
settings:
  show-coordinates: true            # Show spawn coordinates when set
  auto-backup: true                 # Create backups on config reload
  round-coordinates: true           # Round coordinates when setting spawn
  round-to-block-center: true       # Round to block center (0.5 offset)
```

## Sound Types 🔊

Available sounds for `sound-type` and `countdown-sound-type`:

**Teleportation Sounds**:
- `ENTITY_ENDERMAN_TELEPORT` (default)
- `ENTITY_PLAYER_TELEPORT`
- `ENTITY_ENDER_PEARL`

**Countdown Sounds**:
- `BLOCK_NOTE_BLOCK_PLING` (default)
- `BLOCK_NOTE_BLOCK_HAT`
- `BLOCK_NOTE_BLOCK_BELL`
- `UI_BUTTON_CLICK`

## Permissions 🔐

| Permission | Description | Default |
|-----------|-------------|---------|
| `spawn.use` | Allows teleporting to spawn | Everyone |
| `spawn.set` | Allows setting spawn location | Operators |
| `spawn.reload` | Allows reloading configuration | Operators |

## Example Configuration 📝

```yaml
spawn:
  world: world
  x: 100.5
  y: 64.0
  z: 200.5
  yaw: 0.0
  pitch: 0.0

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
  sound-type: ENTITY_PLAYER_TELEPORT
  play-countdown-sound: true
  countdown-sound-type: BLOCK_NOTE_BLOCK_HAT

settings:
  show-coordinates: true
  auto-backup: true
  round-coordinates: true
  round-to-block-center: true
```

## How to Use 📖

### Setting the Spawn
1. Go to the location where you want players to spawn
2. Run `/spawn set` as an operator
3. The coordinates will be automatically rounded to the block center
4. You'll see a confirmation message with the exact coordinates

### Players Teleporting to Spawn
1. Players run `/spawn`
2. They will see a countdown message (configurable delay)
3. Countdown sounds play each second (configurable)
4. After the delay, they teleport with a sound effect
5. A cooldown is applied before they can teleport again

### Reloading Configuration
1. Run `/spawn reload` as an operator
2. A backup of the old config is automatically created
3. Changes take effect immediately

## Customization Tips 💡

### Custom Welcome Message
Change the teleportation countdown message:
```yaml
teleport-countdown: '&b[&fSpawn&b] &7In &6{time}s &7you will go to spawn...'
```

### Disable Sounds
Set `play-sound: false` and `play-countdown-sound: false` to disable all sounds

### Longer Teleportation Delay
Increase the delay value for a longer countdown:
```yaml
delay: 10  # 10 seconds delay
```

### Remove Cooldown
Set cooldown to 0:
```yaml
cooldown: 0  # No cooldown
```

## Troubleshooting 🔧

### Players Can't Teleport
- Check if they have the `spawn.use` permission
- Verify the spawn world exists
- Check if the spawn location is set (run `/spawn set`)

### Invalid Sound Warnings
- Ensure sound names are correct (see [Sound Types](#sound-types) section)
- Use `Sound.valueOf()` compatible names in uppercase

### Coordinates Not Rounding Correctly
- Set `round-coordinates: true`
- Set `round-to-block-center: true` for center alignment

## Development 👨‍💻

This plugin is built with:
- **Language**: Java
- **Framework**: Spigot API
- **Version**: 1.21+
- **Build Tool**: Gradle

## Contributing 🤝

Contributions are welcome! Feel free to open issues or pull requests.

## License 📄

This project is open source and available under the MIT License.

## Support 💬

If you encounter any issues or have questions, please open an issue on the GitHub repository.

---

**Made with ❤️ by skymord**

Last updated: January 2025

