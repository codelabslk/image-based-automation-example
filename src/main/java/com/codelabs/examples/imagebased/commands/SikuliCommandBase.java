package com.codelabs.examples.imagebased.commands;

import java.io.File;
import java.io.IOException;

import org.sikuli.api.DesktopScreenRegion;
import org.sikuli.api.ImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.SikuliRuntimeException;
import org.sikuli.api.Target;
import org.sikuli.api.robot.Keyboard;
import org.sikuli.api.robot.Mouse;
import org.sikuli.api.robot.desktop.DesktopKeyboard;
import org.sikuli.api.robot.desktop.DesktopMouse;

import com.codelabs.examples.constants.Constants;
import com.codelabs.examples.utils.OsUtils;

public class SikuliCommandBase {

	private String openAppName;

	public void open(String app) throws Exception {

		String cmdPrefix;

		// Lets determine what is the OS which we are running the script.
		if (OsUtils.isWindows()) {

			cmdPrefix = Constants.WINDOWS_RUN_CMD_COMMAND_PREFIX;

		} else {
			cmdPrefix = Constants.MAC_OS_RUN_TERMINAL_COMMAND_PREFIX;
		}

		String[] cmd = { cmdPrefix, app };

		Runtime.getRuntime().exec(cmd);

		this.openAppName = app;
	}
	

	public void type(String fileName, String text) {

		ScreenRegion s = new DesktopScreenRegion();
		File image = new File("images" + File.separator + fileName);
		Target imageTarget = new ImageTarget(image);
		ScreenRegion r = s.wait(imageTarget, Constants.IMAGE_COMMAND_TIMEOUT);

		if (r != null) {

			Mouse mouse = new DesktopMouse();
			mouse.click(r.getCenter());
			mouse.doubleClick(r.getCenter());
			Keyboard keyboard = new DesktopKeyboard();
			keyboard.type(text);
		} else {
			throw new SikuliRuntimeException("Cannot find the image in screen. " + fileName);
		}

	}

	public void click(String fileName) {

		ScreenRegion s = new DesktopScreenRegion();
		File image = new File("images" + File.separator + fileName);
		Target imageTarget = new ImageTarget(image);
		ScreenRegion r = s.wait(imageTarget, Constants.IMAGE_COMMAND_TIMEOUT);

		if (r != null) {

			Mouse mouse = new DesktopMouse();
			mouse.click(r.getCenter());

		} else {
			throw new SikuliRuntimeException("Cannot find the image in screen. " + fileName);
		}

	}

	public void doubleClick(String fileName) {

		ScreenRegion s = new DesktopScreenRegion();
		File image = new File("images" + File.separator + fileName);
		Target imageTarget = new ImageTarget(image);
		ScreenRegion r = s.wait(imageTarget, Constants.IMAGE_COMMAND_TIMEOUT);

		if (r != null) {

			Mouse mouse = new DesktopMouse();
			mouse.doubleClick(r.getCenter());

		} else {
			throw new SikuliRuntimeException("Cannot find the image in screen. " + fileName);
		}

	}

	public void mouseOver(String fileName) {

		ScreenRegion s = new DesktopScreenRegion();
		File image = new File("images" + File.separator + fileName);
		Target imageTarget = new ImageTarget(image);
		ScreenRegion r = s.wait(imageTarget, Constants.IMAGE_COMMAND_TIMEOUT);

		if (r != null) {

			Mouse mouse = new DesktopMouse();
			mouse.drop(r.getCenter());

		} else {
			throw new SikuliRuntimeException("Cannot find the image in screen. " + fileName);
		}

	}

	public void waitForImage(String fileName, int waitingTime) {

		ScreenRegion s = new DesktopScreenRegion();
		File image = new File("images" + File.separator + fileName);
		Target imageTarget = new ImageTarget(image);
		ScreenRegion r = s.wait(imageTarget, waitingTime);

		if (r == null) {
			throw new SikuliRuntimeException("Cannot find the image in screen. " + fileName);

		}

	}

	public ScreenRegion getRelativeImage(String fileName, int xOffset, int yOffset, int w, int h) {

		ScreenRegion s = new DesktopScreenRegion();
		File image = new File("images" + File.separator + fileName);
		Target imageTarget = new ImageTarget(image);
		ScreenRegion r = s.wait(imageTarget, Constants.IMAGE_COMMAND_TIMEOUT);

		if (r != null) {

			ScreenRegion r1 = r.getRelativeScreenRegion(xOffset, yOffset, w, h);
			return r1;
		} else {
			throw new SikuliRuntimeException("Cannot find the image in screen. " + fileName);
		}

	}

	public void clickAt(String fileName, int x, int y) {

		ScreenRegion s = new DesktopScreenRegion();
		File image = new File("images" + File.separator + fileName);
		Target imageTarget = new ImageTarget(image);
		ScreenRegion r = s.wait(imageTarget, Constants.IMAGE_COMMAND_TIMEOUT);

		if (r != null) {

			Mouse mouse = new DesktopMouse();

			mouse.click(r.getRelativeScreenLocation(x, y));

		} else {
			throw new SikuliRuntimeException("Cannot find the image in screen. " + fileName);
		}

	}

	public void closeApp() throws IOException {
		String[] cmd = { "/usr/bin/osascript", "-e quit app \"" + openAppName + "\"'" };
		Runtime.getRuntime().exec(cmd);
	}

}
