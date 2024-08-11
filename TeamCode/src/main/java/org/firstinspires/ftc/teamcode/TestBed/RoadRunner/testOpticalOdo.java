package org.firstinspires.ftc.teamcode.TestBed.RoadRunner;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@TeleOp(name = "OdoTest", group = "TestBed")
public class testOpticalOdo extends LinearOpMode {

    private GamepadEx driver1, driver2;
    private DcMotorEx mFL, mFR, mBL, mBR;
    double FB, LR, Rotation;
    SparkFunOTOS myOtos;
    @Override
    public void runOpMode(){


        driver1 = new GamepadEx(gamepad1);
        driver2 = new GamepadEx(gamepad2);

        myOtos = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");

        mFL = hardwareMap.get(DcMotorEx.class, "mFL");
        mFR = hardwareMap.get(DcMotorEx.class, "mFR");
        mBL = hardwareMap.get(DcMotorEx.class, "mBL");
        mBR = hardwareMap.get(DcMotorEx.class, "mBR");

        //this motor physically runs opposite. For convenience, reverse direction.
        mBR.setDirection(DcMotorSimple.Direction.REVERSE);
        //mFR.setDirection(DcMotorSimple.Direction.REVERSE);

        configureOtos();

        waitForStart();

        while (opModeIsActive()) {

            FB = gamepad1.left_stick_y;
            LR = -gamepad1.left_stick_x;
            Rotation = -gamepad1.right_stick_x;

            //map motor power to vars (tb tested)
            //depending on the wheel, forward back, left right, and rotation's power may be different
            //think, if fb is positive, thus the bot should move forward, will the motor drive the bot forward if its power is positive.
            mFL.setPower(FB + LR + Rotation);
            mFR.setPower(FB - LR - Rotation);
            mBL.setPower(FB - LR + Rotation);
            mBR.setPower(FB + LR - Rotation);

            SparkFunOTOS.Pose2D pos = myOtos.getPosition();

            // Reset the tracking if the user requests it
            if (gamepad1.y) {
                myOtos.resetTracking();
            }

            // Re-calibrate the IMU if the user requests it
            if (gamepad1.x) {
                myOtos.calibrateImu();
            }

            // Inform user of available controls
            telemetry.addLine("Press Y (triangle) on Gamepad to reset tracking");
            telemetry.addLine("Press X (square) on Gamepad to calibrate the IMU");
            telemetry.addLine();

            // Log the position to the telemetry
            telemetry.addData("Optical X coordinate", pos.x);
            telemetry.addData("Optical Y coordinate", pos.y);
            telemetry.addData("Heading angle", pos.h);


            // Update the telemetry on the driver station
            telemetry.update();


        }
    }

    private void configureOtos() {
        telemetry.addLine("Configuring OTOS...");
        telemetry.update();

        // myOtos.setLinearUnit(DistanceUnit.METER);
        myOtos.setLinearUnit(DistanceUnit.INCH);
        // myOtos.setAngularUnit(AnguleUnit.RADIANS);
        myOtos.setAngularUnit(AngleUnit.DEGREES);

        SparkFunOTOS.Pose2D offset = new SparkFunOTOS.Pose2D(0, 0, 0);
        myOtos.setOffset(offset);

        myOtos.setLinearScalar(1.0);
        myOtos.setAngularScalar(1.0);

        myOtos.calibrateImu();

        myOtos.resetTracking();

        SparkFunOTOS.Pose2D currentPosition = new SparkFunOTOS.Pose2D(0, 0, 0);
        myOtos.setPosition(currentPosition);

        // Get the hardware and firmware version
        SparkFunOTOS.Version hwVersion = new SparkFunOTOS.Version();
        SparkFunOTOS.Version fwVersion = new SparkFunOTOS.Version();
        myOtos.getVersionInfo(hwVersion, fwVersion);

        telemetry.addLine("OTOS configured! Press start to get position data!");
        telemetry.addLine();
        telemetry.addLine(String.format("OTOS Hardware Version: v%d.%d", hwVersion.major, hwVersion.minor));
        telemetry.addLine(String.format("OTOS Firmware Version: v%d.%d", fwVersion.major, fwVersion.minor));
        telemetry.update();
    }

}
