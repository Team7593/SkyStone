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


    double position = robot.HOME;
    final double SPEED = 0.02; //sets rate to move servo

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

        /*robot.tilt.setPower(0);
        robot.rightLift.setPower(0);
        robot.leftLift.setPower(0);

        robot.tilt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.leftLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.rightLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        robot.tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //get the starting encoder value of tilt (this is so we don't assume the starting econder value is 0)
        oldEncoderVal = robot.tilt.getCurrentPosition();
        oEncoderVal = robot.leftLift.getCurrentPosition();
        olEncoderVal = robot.rightLift.getCurrentPosition();*/

        telemetry.addData("Say", "HELLO FROM THE OTHER SIIIIIDE");

    }

    public void loop() {

        //use super's loop so that auton steps can run
        super.loop();

        //get the current encoder value of tilt
        /*cuEncoderVal = robot.leftLift.getCurrentPosition();
        cEncoderVal = robot.rightLift.getCurrentPosition();
        currEncoderVal = robot.tilt.getCurrentPosition();*/

        double leftX, rightX, leftY, liftStick, tiltStick, tiltPower; //declaration for the game sticks + power
        boolean hook, latch, slowDrive, slowTilt, slowDrive2; //declaration for the buttons/bumpers
        WheelSpeeds speeds; //variable to hold speeds

        leftX = gamepad1.left_stick_x;
        rightX = gamepad1.right_stick_x;
        leftY = gamepad1.left_stick_y;
        slowDrive = gamepad1.left_bumper;
        slowDrive2 = gamepad1.right_bumper;


        tiltPower = .6;


        //get the speeds
        if(slowDrive || slowDrive2){
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, true);
        }else{
            speeds = WheelSpeeds.mecanumDrive(leftX, leftY, rightX, false);
        }

        //power the motors
        robot.powerTheWheels(speeds);


                //code to turn servo
        if(gamepad1.dpad_up){
            position += SPEED;
            position = Range.clip(position, robot.MIN, robot.MAX);
            robot.drop1.setPosition(position);
            robot.drop2.setPosition(position);
        }else if(gamepad1.dpad_down){
            position -= SPEED;
            position = Range.clip(position, robot.MIN, robot.MAX);
            robot.drop1.setPosition(position);
            robot.drop2.setPosition(position);
        }

        if(gamepad1.a){
            robot.drop1.setPosition(0);
            robot.drop2.setPosition(0);
        }

        if(gamepad1.a){
            robot.drop1.setPosition(.8);
            robot.drop2.setPosition(.8);
        }


//        //slow the tilt motor
//        if(slowTilt){
//            tiltPower = tiltPower/2;
//        }

        /*if(liftStick > 0) {
            if (robot.rightLift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            if(robot.leftLift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.rightLift.setPower(liftStick);
            robot.leftLift.setPower(-liftStick);
            cEncoderVal = oEncoderVal;
            cuEncoderVal = olEncoderVal;
        }else if(liftStick < 0) {
            if (robot.rightLift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
            robot.rightLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
            if(robot.leftLift.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.leftLift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.rightLift.setPower(liftStick);
            robot.leftLift.setPower(-liftStick);
            cEncoderVal = oEncoderVal;
            cuEncoderVal = olEncoderVal;

        }else{
            robot.rightLift.setTargetPosition(oEncoderVal);
            robot.leftLift.setTargetPosition(oldEncoderVal);
            if (robot.rightLift.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
            robot.rightLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            if (robot.leftLift.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                robot.leftLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            robot.rightLift.setPower(0.1);
            robot.leftLift.setPower(0.1);
        }

        if (tiltStick > 0) {
            if (robot.tilt.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.tilt.setPower(tiltStick);
            oldEncoderVal = currEncoderVal;
        } else if (tiltStick < 0) {
            if (robot.tilt.getMode() != DcMotor.RunMode.RUN_USING_ENCODER) {
                robot.tilt.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            }
            robot.tilt.setPower(tiltStick);
            oldEncoderVal = currEncoderVal;
        } else {
            robot.tilt.setTargetPosition(oldEncoderVal);
            if (robot.tilt.getMode() != DcMotor.RunMode.RUN_TO_POSITION) {
                robot.tilt.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            }
            robot.tilt.setPower(0.1);
        }
         */

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