# Plugin Hotkeys

Bind a hotkey to any installed plugin to switch it on or off without opening the plugin list.

## Setup

The settings have 10 slots. Each slot has:

- **Plugin** - the name of the plugin to toggle, as shown in the plugin list. Capitals and punctuation
  don't matter, and a unique part of the name is enough (`npc notifier` finds `[S] Npc Notifier`). If two
  plugins match, the plugin says so in chat and does nothing rather than guess; type more of the name.
  Separate several plugins with commas.
- **Hotkey** - press it to switch the plugin(s) on or off. Use a modifier such as Ctrl or Alt so the key
  doesn't fire while you type in chat.

## Settings

- **Chat message** - say in chat what a hotkey did, or why it couldn't.
- **Several plugins** - what a hotkey does when a slot lists more than one plugin:
  - *Sync: all on or all off* - if the first plugin is on, they all go off, otherwise they all go on.
  - *Flip: each one swaps* - every plugin switches to the opposite of its own state.

A hotkey can't toggle Plugin Hotkeys itself, or plugins that RuneLite hides from the plugin list.
