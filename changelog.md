### 0.5.x

#### 0.5.5
Add MurderMystery mode:
- Use '/cbr register manager "murdermystery:MMGameProcessManager"' to switch to MurderMystery, and use '/cbr register manager "battleroyale:GameManager"' to switch back
- Add MurderMysteryProcessManager api command
- Auto set role with delay, tick corresponding zone funcs when survivor/detective/murder set
- Filter survivor/murder item when pickup
- Add CountdownEvent, SetRoleEvent, DelayedRoleKillEvent to custom event class
- Add survive time goal progress bar

Datapack:
- Register all murder mystery event tag
- Add wrong kill punishment
- Add set-role message, effect
- Add countdown sounds
- Add extra sound to murderer death