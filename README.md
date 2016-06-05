Minecraft FluidUI
=================

A better UI system for Minecraft Forge.

### Why?
Why should I have to use a small box in the centre of my screen, and have to try and fit all of my components into an area that's way too small, and have to program the mess that are my UI components?

The UI system for my mod Modular FluxFields used to be a **huge** mess. I hated having to create it, modify it, and deal with the odd ways of Minecraft's UI system. (For Example: Why do I draw a rectangle by defining it's edges as distances from the 4 edges of my screen? Could I not just use the top left point as `0, 0`)

I ended up looking into OpenGL and drawing components using LWJGL directly, instead of going through Minecraft's drawing system. I also made it poll the keyboard when the GUI is open so that I get more control.

Also, text fields use the proper keyboard shortcuts on a Mac! (Using the Option key to navigate words, and the Command key to navigate to the beginning/end - It's not Command to navigate words Mojang!)

### Screenshots

These screenshots are taken from Modular Fluxfields - A mod I'm working on  
http://imgur.com/a/on7jU

![Info](http://i.imgur.com/ORIQE64.png)
Info

![Sizing](http://i.imgur.com/R0x4zZj.png)
Sizing

![Security](http://i.imgur.com/5EQ2al2.png)
Security

![Security with components highlighted](http://i.imgur.com/Zukpmab.png)
Security - With debug mode enabled - Components are highlighted in red  
Debug mode is enabled by pressing F3 - Highlight components by mousing over a component on the left

![Power Statistics](http://i.imgur.com/xhKtcS7.png)
Power Statistics
