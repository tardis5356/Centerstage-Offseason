package org.firstinspires.ftc.teamcode.DEMOS.FRED;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.DEMOS.FRED.subsystems.FredArm;
import org.firstinspires.ftc.teamcode.DEMOS.FRED.subsystems.FredWrist;

@TeleOp(name = "AiTeleop", group = "Teleop")
public class AiExperimentalTele extends CommandOpMode {

    private GamepadEx drive, appendage;
    private DcMotorEx mFL, mFR, mBL, mBR;
    private FredArm arm;
    private FredWrist wrist;
    private ElapsedTime runtime = new ElapsedTime();

    private static final double SPIN_POWER = 0.5;
    private static final double PROGRAMMED_MOVE_POWER = 0.3;
    private static final int PROGRAMMED_MOVE_TIME = 500; // milliseconds

    @Override
    public void initialize() {
        Telemetry telemetry = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());

        drive = new GamepadEx(gamepad1);
        appendage = new GamepadEx(gamepad2);

        mBL = hardwareMap.get(DcMotorEx.class, "mBL");
        mBR = hardwareMap.get(DcMotorEx.class, "mBR");
        mFL = hardwareMap.get(DcMotorEx.class, "mFL");
        mFR = hardwareMap.get(DcMotorEx.class, "mFR");

        arm = new FredArm(hardwareMap);
        wrist = new FredWrist(hardwareMap);

        setupTriggers();
    }

    private void setupTriggers() {
        new Trigger(() -> drive.getButton(GamepadKeys.Button.X))
                .whenActive(this::spin360);

        new Trigger(() -> drive.getButton(GamepadKeys.Button.Y))
                .whenActive(this::spin720);

        new Trigger(() -> drive.getButton(GamepadKeys.Button.A))
                .whenActive(() -> programmedMove("forward"));

        new Trigger(() -> drive.getButton(GamepadKeys.Button.B))
                .whenActive(() -> programmedMove("right"));

        new Trigger(() -> appendage.getButton(GamepadKeys.Button.DPAD_UP))
                .whenActive(wrist::wristUp);

        new Trigger(() -> appendage.getButton(GamepadKeys.Button.DPAD_DOWN))
                .whenActive(wrist::wristDown);

        new Trigger(() -> opModeIsActive())
                .whenActive(wrist::innitializeWrist);
    }

    @Override
    public void run() {
        super.run();

        // Omnidirectional drive control
        double y = -drive.getLeftY();
        double x = drive.getLeftX();
        double rx = drive.getRightX();

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        mFL.setPower(frontLeftPower);
        mBL.setPower(backLeftPower);
        mFR.setPower(frontRightPower);
        mBR.setPower(backRightPower);

        // Manual arm control (if needed)
        // arm.ManualMode(appendage.getLeftY(), -appendage.getRightY());
    }

    private void spin360() {
        spin(1);
    }

    private void spin720() {
        spin(2);
    }

    private void spin(int rotations) {
        runtime.reset();
        while (runtime.milliseconds() < 1000 * rotations) {
            setAllMotors(SPIN_POWER);
        }
        stopAllMotors();
    }

    private void programmedMove(String direction) {
        for (int i = 0; i < 3; i++) {
            runtime.reset();
            while (runtime.milliseconds() < PROGRAMMED_MOVE_TIME) {
                switch (direction) {
                    case "forward":
                        setMotors(PROGRAMMED_MOVE_POWER, PROGRAMMED_MOVE_POWER, PROGRAMMED_MOVE_POWER, PROGRAMMED_MOVE_POWER);
                        break;
                    case "right":
                        setMotors(PROGRAMMED_MOVE_POWER, -PROGRAMMED_MOVE_POWER, -PROGRAMMED_MOVE_POWER, PROGRAMMED_MOVE_POWER);
                        break;
                }
            }
            stopAllMotors();
            sleep(200); // Brief pause between movements
        }
    }

    private void setAllMotors(double power) {
        setMotors(power, power, power, power);
    }

    private void setMotors(double fl, double fr, double bl, double br) {
        mFL.setPower(fl);
        mFR.setPower(fr);
        mBL.setPower(bl);
        mBR.setPower(br);
    }

    private void stopAllMotors() {
        setAllMotors(0);
    }
}