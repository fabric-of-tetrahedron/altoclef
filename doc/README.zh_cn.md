# AltoClef
*Plays block game.*

*Powered by Baritone.*

A client side bot that tries to beat Minecraft on its own...

**This fork is still under development and is nowhere near perfect, if you have any questions, suggestions, ideas or find a bug don't hesitate to reach out!
You can use the [issues](https://github.com/MiranCZ/altoclef/issues). Or contact me on discord!**

Became [the first bot to beat Minecraft fully autonomously](https://youtu.be/baAa6s8tahA) on May 24, 2021.

**Join the [Discord Server](https://discord.gg/JdFP4Kqdqc)** for discussions/updates/goofs & gaffs

## About this fork (MiranCZ)
This fork aims to optimize `MarvionBeatMinecraftTask` (I will just refer to it as `BeatMinecraftTask`) from [Marvion's fork](https://github.com/MarvionKirito/altoclef) by trying to fix a lot of cases where the bot gets stuck and improving some of the tasks.

You can look at the [changelog](changelog.md) if you are interested.

Because I rewrote a good portion of the `BeatMinecraftTask` a lot of the config settings don't work. Although I plan to implement configs in the future of course.

## 关于这个分支（pama1234）

这个分支项目将会添加“通过llm生成java代码并在运行时注入到游戏中来完成更多的任务的功能“，类似于<https://github.com/MineDojo/Voyager>，但是完全由java编写，并且通过机器人脚本实现AI直接操作游戏较难达到的效果

### The preprocessor
I am currently experimentaly using the [replay mod preprocessor](https://github.com/ReplayMod/preprocessor) to keep the mod updated across multiple versions at the same time.
The thing that is holding me back right now, is that I am using [Marvions releases for baritone](https://github.com/MarvionKirito/baritone) to go along AltoClef, so I should be able to at least keep up with [his fork](https://github.com/MarvionKirito/altoclef) for now.

...

(Section not finished)
## How it works

Take a look at this [Guide from the wiki](https://github.com/MiranCZ/altoclef/wiki/1:-Documentation:-Big-Picture)
or this [Video explanation](https://youtu.be/q5OmcinQ2ck?t=387)


## Download

**Note:** After installing, please move/delete your old baritone configurations if you have any. Preexisting baritone
configurations will interfere with altoclef and introduce bugs. This will be fixed in the future.

[Check releases](https://github.com/MiranCZ/altoclef/releases)

### Versions

This is a **fabric only** mod, currently only available only for **Minecraft 1.20.2**.

## [Usage Guide](usage.md)

## [TODO's/Future Features](TODO.md)

## [Development Guide](develop.md)
