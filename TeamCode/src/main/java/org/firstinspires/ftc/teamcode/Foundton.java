package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.ArrayList;

@Autonomous (name = "Foundton")
public class Foundton extends Team7593OpMode {


    @Override
    public void init(){
        super.init();
    }

    @Override
    public ArrayList<AutonStep> createAutonSteps() {
        ArrayList<AutonStep> steps = new ArrayList<>();

        //steps.add(new Color(0.8));
        /*steps.add(new DriveY(.35, .5));
        steps.add(new DriveY(.1, .4));
        steps.add(new DriveX(.5, -.6));
        steps.add(new DriveY(.5, -.5));
        steps.add(new DriveX(.5, .6));
        steps.add(new DriveY(.5, .5));
        steps.add(new DriveY(.1, -.5));
        steps.add(new DriveX (.75, -.6));*/


        return steps;
    }
}
