# This program is used to automate the daunting task of manually resetting for a chance at encountering a shiny pokemon, a tremendously rare colour alteration
# At 30s/encounter, this task takes on average +70hrs to do in game
# Used in tandem with an arduino to automate the process of repeatingly pressing 'A' button, checking for colour alterations, and resetting the game

# First attempt with currunt code found success after 2207 encounters and resets! (1/8192 odds)


import numpy
import cv2
import time
import serial

# All the serial port stuff here first!
ser = serial.Serial()
ser.baudrate = 9600
ser.port = 'COM3'
ser.open()

pressA = '1' #not used
reset = '2'
shiny_ = '3'

hat = False
shiny = False
count = 0

#Pixel coordinates of the hat
hatPixelsX = 155
hatPixelsY = 221

#Pixel coordinates of the Pokemon we're checking
shinyCheckPixelsX = 410
shinyCheckPixelsY = 120

#RGB values of the player's hat
hatColourR = 250
hatColourG = 135
hatColourB = 190

firstEncounter = True

cap = cv2.VideoCapture(0)
cap.set(cv2.CAP_PROP_BUFFERSIZE,1)

while (True):

    ret, frame = cap.read() 

    if cv2.waitKey(1)& 0xFF == ord('q'):
        break
   
    if hat == False: 
        
        #check the hat pixel value
        hatColourArray = frame[hatPixelsY, hatPixelsX]
        #print(hatColourArray) <-- used for testing

        #set borders around target pixels
        #Hat location marker

        frame[hatPixelsY -1, hatPixelsX][0] = 255
        frame[hatPixelsY -1, hatPixelsX][1] = 255
        frame[hatPixelsY -1, hatPixelsX][2] = 255

        frame[hatPixelsY , hatPixelsX+1][0] = 255
        frame[hatPixelsY , hatPixelsX+1][1] = 255
        frame[hatPixelsY , hatPixelsX+1][2] = 255  
       
        frame[hatPixelsY+1 , hatPixelsX][0] = 255
        frame[hatPixelsY+1 , hatPixelsX][1] = 255
        frame[hatPixelsY+1 , hatPixelsX][2] = 255

        frame[hatPixelsY , hatPixelsX-1][0] = 255
        frame[hatPixelsY , hatPixelsX-1][1] = 255
        frame[hatPixelsY , hatPixelsX-1][2] = 255

        #shiny check marker

        frame[shinyCheckPixelsY - 1 , shinyCheckPixelsX][0] = 255
        frame[shinyCheckPixelsY - 1 , shinyCheckPixelsX][1] = 0
        frame[shinyCheckPixelsY - 1 , shinyCheckPixelsX][2] = 255

        frame[shinyCheckPixelsY , shinyCheckPixelsX + 1][0] = 255
        frame[shinyCheckPixelsY , shinyCheckPixelsX + 1][1] = 0
        frame[shinyCheckPixelsY , shinyCheckPixelsX + 1][2] = 255

        frame[shinyCheckPixelsY + 1 , shinyCheckPixelsX][0] = 255
        frame[shinyCheckPixelsY + 1 , shinyCheckPixelsX][1] = 0
        frame[shinyCheckPixelsY + 1 , shinyCheckPixelsX][2] = 255

        frame[shinyCheckPixelsY , shinyCheckPixelsX - 1][0] = 255
        frame[shinyCheckPixelsY , shinyCheckPixelsX - 1][1] = 0
        frame[shinyCheckPixelsY , shinyCheckPixelsX - 1][2] = 255
       
        cv2.imshow('frame',frame) # update frame

        #for testing purposes
        #print("Hat--> " + "Blue: " + str(hatColourArray[0]) + " Green: " + str(hatColourArray[1]) + " Red: " + str(hatColourArray[2]))
       
       #the following checks if the player's hat is in view. Once in view, we can check for shiny
        if (hatColourB -30 < hatColourArray[0]) and (hatColourArray[0] < hatColourB + 30) :
            if (hatColourG -30 < hatColourArray[1]) and (hatColourArray[1] < hatColourG + 30) :
                if (hatColourR -30 < hatColourArray[2]) and (hatColourArray[2] < hatColourR + 30) :
                    hat = True

    #not needed
    #if hat == False:
        #press a button
        #ser.write(pressA.encode())
        #ser.write(reset.encode())
        #time.sleep(0.5)
        
    #Check for shiny!
    if hat == True :
        hat = False # In case we need to reset 
        time.sleep(4) # allow some time for pokemon appear and finish animation
        ret, frame = cap.read()
        shinyCheckColourArray = frame[shinyCheckPixelsY,shinyCheckPixelsX] 

        print("Shiny check--> " + "Blue: " + str(shinyCheckColourArray[0]) + " Green: " + str(shinyCheckColourArray[1]) + " Red: " + str(shinyCheckColourArray[2]))


        if firstEncounter == True:
            firstEncounterColourArray = shinyCheckColourArray #assumes the first encounter won't be shiny 
            firstEncounter = False

        if (firstEncounterColourArray[0] + 30 > shinyCheckColourArray[0]) and (shinyCheckColourArray[0]> firstEncounterColourArray[0] - 30):
            if (firstEncounterColourArray[1] + 30 > shinyCheckColourArray[1]) and (shinyCheckColourArray[1]> firstEncounterColourArray[1] - 30):
                if (firstEncounterColourArray[2] + 30 > shinyCheckColourArray[2]) and (shinyCheckColourArray[2]> firstEncounterColourArray[2] - 30):
                    #no drastic colour change, not shiny
                    print("Not Shiny:(")
                    print("count = " + str(count)) 
                else:
                    shiny = True
            else:
                shiny = True
        else:
            shiny = True

        if shiny == True:
            #send serial data to arduino to terminate program
            ser.write(shiny_.encode())
            print("Shiny found at " + str(count))
            cap.release
            cv2.destroyAllWindows
            while(True):
                pass
        else:
            ser.write(reset.encode())
            #send serial data to arduino to soft reset game
            count = count + 1
            time.sleep(1)

#not really needed           
cap.release
cv2.destroyAllWindows
