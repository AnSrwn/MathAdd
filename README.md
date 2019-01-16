# MathAdd

This application was developed as a project in the subject Mobile Devices. The assignment was to develop an application for Android, in which the user needs to summate two numbers. For this task the user must give the carry and the sum.

In the final version, the application is structured as a game. After starting the game, the user gets two random generated numbers, which he/she needs to summate. Under the two buttons there are buttons for the carry and the final sum. By clicking on a button, the number is increased by one or alternatively continuously, if you do a long click. If the numbers for carry and sum are correct a toast saying “Correct” appears. While playing, a timer measures how fast the user solves the problem. If he/she is faster than the fastest time in the high score, a dialog window appears, in which the user can type in his/her name.
 
Figure 1 GameActivity

In the image above you can see the GameActivity, in which you can see the described numbers and the buttons for carry and sum. Above the numbers, is the mentioned timer. Under the buttons for the sum, is a button to restart the game. If you click this button two new numbers are generated, and the time is set back to zero.

In the MainActivity are only two buttons. One to start a game, which leads to the GameActivity, and one to open the high score. In the high score is a list of maximum ten names with the corresponding times. They are listed from the fastest time to the slowest. This looks like the following image.
 
Figure 2 HighScore
