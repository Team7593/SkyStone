package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import java.util.ArrayList;

@Autonomous (name = "Parkton")
public class Parkton extends Team7593OpMode {


    @Override
    public void init(){
        super.init();
    }

    @Override
    public ArrayList<AutonStep> createAutonSteps() {
        ArrayList<AutonStep> steps = new ArrayList<>();

        steps.add(new DriveY(.2, -.6));
        steps.add(new DriveX(1, -.75));

        return steps;
    }


}
