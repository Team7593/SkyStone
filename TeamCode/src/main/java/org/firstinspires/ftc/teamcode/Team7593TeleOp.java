package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.ArrayList;

@TeleOp (name = "TeleOp")
public class Team7593TeleOp extends Team7593OpMode {


    double position1 = robot.HOME;
    double position2 = robot.MAX;
    final double SPEED = 0.02; //sets rate to move servo

    public int cEncoderVal;
    public int oEncoderVal;

    public int currEncoderVal;
    public int oldEncoderVal;

    Orientation angles; //to use the imu (mostly for telemetry)

    //double extensionPosition = robot.EXTENSION_HOME;
    final double EXTENSION_SPEED = 0.08; //sets rate to move servo

    @Override
    //code block to that will run at the VERY beginning of Teleop
    public ArrayList<AutonStep> createAutonSteps() {
        return null;
    }

    @Override
    public void init(){
        super.init();

        //stop the motor(s) and reset the motor encoders to 0
        robot.out.setPower(0);
        robot.up.setPower(0);

        robot.claw.setPosition(0);

        robot.up.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.up.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //get the starting encoder value of tilt (this is so we don't assume the starting econder value is 0)
        oEncoderVal = robot.up.getCurrentPosition();
        oldEncoderVal = robot.out.getCurrentPosition();

        telemetry.addData("Say", "HELLO FROM THE OTHER SIIIIIDE");

    }

    public void loop() {

        //use super's loop so that auton steps can run
        super.loop();

        //get the current encoder value of tilt
        cEncoderVal = robot.up.getCurrentPosition();
        currEncoderVal = robot.out.getCurrentPosition();

        double leftX, rightX, leftY, out, up, outPower, upPower; //declaration for the game sticks + power
        boolean slow, slow1, slowDrive, slowDrive2, spin; //declaration for the buttons/bumpers
        WheelSpeeds speeds; //variable to hold speeds

        leftX = gamepad1.left_stick_x;
        rightX = gamepad1.right_stick_x;
        leftY = gamepad1.left_stick_y;
        slowDrive = gamepad1.left_bumper;
        slowDrive2 = gamepad1.right_bumper;
        slow = gamepad2.right_bumper;
        slow1 = gamepad2.left_bumper;
        up = gamepad2.left_stick_y;
        out = gamepad2.right_stick_y;
        spin = gamepad2.x;

        upPower = up*.75;
        outPower = out*.75;

        if(spin){
            robot.spin1.setPower(-1);
            robot.spin2.setPower(1);
        }

        //get the speeds
        if(slowDrive || slowDrive2){
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, true);
        }else{
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, false);
        }

        //power the motors
        robot.powerTheWheels(speeds);

        if(gamepad2.a){
            robot.claw.setPosition(.7);
        }else if(gamepad2.b){
            robot.claw.setPosition(0);
        }


        if(slow || slow1){
            upPower = up*.5;
            outPower = out*.5;
        }

        //recursive encoder loop to the keep the tilt motor still-ish
        if (up > 0) {
            if (robot.up.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.up.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.up.setPower(upPower);
            oldEncoderVal = currEncoderVal;
        } else if (up < 0) {
            if (robot.up.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.up.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.up.setPower(upPower);
            oldEncoderVal = currEncoderVal;
        } else {
            if (robot.up.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                robot.up.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            robot.up.setTargetPosition(oldEncoderVal);
            robot.up.setPower(0.05);
        }

        if (out > 0) {
            robot.out.setPower(outPower);
        } else if (up < 0) {
            robot.out.setPower(outPower);
        } else {
            robot.out.setPower(0);
        }


        //use the imu
        angles = robot.imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);


        /*
        WHOA TELEMETRY
        */
        telemetry.addLine().addData("slow mode: ", slowDrive);


        //angles on each of the axes
        telemetry.addLine().addData("IMU angle Y:", angles.secondAngle);
        telemetry.addData("IMU angle Z:", angles.firstAngle);
        telemetry.addData("IMU angle X:", angles.thirdAngle);

        //angles it's at
        telemetry.addLine();
        telemetry.addData("Current Angle: ", robot.getCurrentAngle());
        telemetry.addData("Init Angle: ", robot.initAngle);
    }
}