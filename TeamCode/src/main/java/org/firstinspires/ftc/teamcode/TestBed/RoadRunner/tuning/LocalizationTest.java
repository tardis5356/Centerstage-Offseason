package org.firstinspires.ftc.teamcode.TestBed.RoadRunner.tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.hardware.sparkfun.SparkFunOTOS;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.TestBed.RoadRunner.Drawing;
import org.firstinspires.ftc.teamcode.TestBed.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.TestBed.RoadRunner.TankDrive;


public class LocalizationTest extends LinearOpMode {

    SparkFunOTOS myOtos;
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        myOtos = hardwareMap.get(SparkFunOTOS.class, "sensor_otos");

        configureOtos();

        if (TuningOpModes.DRIVE_CLASS.equals(MecanumDrive.class)) {
            MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(0, 0, 0));


            waitForStart();

            while (opModeIsActive()) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                -gamepad1.left_stick_y,
                                -gamepad1.left_stick_x
                        ),
                        -gamepad1.right_stick_x
                ));

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


                drive.updatePoseEstimate();

                telemetry.addData("RRx", drive.pose.position.x);
                telemetry.addData("RRy", drive.pose.position.y);
                telemetry.addData("RRheading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
                telemetry.update();

                TelemetryPacket packet = new TelemetryPacket();
                packet.fieldOverlay().setStroke("#3F51B5");
                Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
                FtcDashboard.getInstance().sendTelemetryPacket(packet);
            }
        } else if (TuningOpModes.DRIVE_CLASS.equals(TankDrive.class)) {
            TankDrive drive = new TankDrive(hardwareMap, new Pose2d(0, 0, 0));


            waitForStart();

            while (opModeIsActive()) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                -gamepad1.left_stick_y,
                                0.0
                        ),
                        -gamepad1.right_stick_x
                ));

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


                drive.updatePoseEstimate();

                telemetry.addData("RRx", drive.pose.position.x);
                telemetry.addData("RRy", drive.pose.position.y);
                telemetry.addData("RRheading (deg)", Math.toDegrees(drive.pose.heading.toDouble()));
                telemetry.update();

                TelemetryPacket packet = new TelemetryPacket();
                packet.fieldOverlay().setStroke("#3F51B5");
                Drawing.drawRobot(packet.fieldOverlay(), drive.pose);
                FtcDashboard.getInstance().sendTelemetryPacket(packet);
            }
        } else {
            throw new RuntimeException();
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
