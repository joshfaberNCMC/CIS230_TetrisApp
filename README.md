This app is loosely based on this set of YouTube tutorials:  
https://www.youtube.com/watch?v=qZH_wrwU-L4&list=PLmlCMM6OshBQDPJFLE-NhHqmReB4P_j1W&index=1

I had to heavily refactor the code from those videos to clean up poor coding practices and get it to a playable state, so it is a substantially different project. I also added some menus and additonal functionality to flesh out the application so that it wasn't just the Tetris game window.

The code is not perfect - there are still several bugs in the deleteLine() and rotate() methods. Sometimes line clears introduce phantom blocks which prevent new falling blocks from landing where they are supposed to, and rotating some blocks that are up against the left or right edge of the game grid will cause the blocks to split and part of the block will appear on the opposite side. The game is totally playable though and issues are rare, so I deemed current functionality good enough for my semester project.

![Screenshot 2024-12-10 145224](https://github.com/user-attachments/assets/f3acfd33-df46-4cce-b863-322722a9703b)
![Screenshot 2024-12-10 145252](https://github.com/user-attachments/assets/ce104db9-d21f-4a28-b187-3b8b6f42081b)
![Screenshot 2024-12-10 145316](https://github.com/user-attachments/assets/c6ce51f9-3db6-4b0a-96d4-ad06d64d11a0)
![Screenshot 2024-12-10 145408](https://github.com/user-attachments/assets/97bb5ec3-f7b0-4598-9a49-5d1f511fa2fa)
![Screenshot 2024-12-10 145427](https://github.com/user-attachments/assets/83fd96cc-4884-476c-bcbd-1d40fc6a71d7)
![Screenshot 2024-12-10 145447](https://github.com/user-attachments/assets/debec1fc-0450-48a0-a5a6-1c55d4d79198)
