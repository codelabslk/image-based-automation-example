package com.codelabs.examples.imagebased.commands;

import java.io.File;
import java.io.FileNotFoundException;
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

	public ScreenRegion findImageElement(String imageName) throws SikuliRuntimeException, FileNotFoundException {

		// Finds the image locator in the images folder in the project
		File target = getImageFromFile(imageName);

		// Create a screen region we need to find this target element.
		ScreenRegion desktopScreen = new DesktopScreenRegion();
		Target imageTarget = new ImageTarget(target);
		ScreenRegion targetRegion = desktopScreen.wait(imageTarget, Constants.IMAGE_COMMAND_TIMEOUT);
		// At this point, if the element is found, the tarhgetRegion will be not null.

		if (targetRegion != null) {
			return targetRegion;
		} else {
			throw new SikuliRuntimeException(
					"Image element could not be located using the given image locator : " + imageName);
		}

	}

	private File getImageFromFile(String imageName) throws FileNotFoundException {

		File image = new File("images" + File.separator + imageName);
		// Check if the file exists. Else throw an error with the details
		if (image.exists()) {
			return image;
		} else {
			throw new FileNotFoundException(imageName + " does not exist in the Images folder");
		}

	}

	public void type(String fileName, String text) throws SikuliRuntimeException, FileNotFoundException {

		ScreenRegion targetRegion = findImageElement(fileName);
		// First lets mimic a user's type action. We click and type on an element right?
		Mouse mouse = new DesktopMouse();
		mouse.click(targetRegion.getCenter());

		// Now lets type
		Keyboard keyboard = new DesktopKeyboard();
		keyboard.type(text);

	}

	public void click(String fileName) throws SikuliRuntimeException, FileNotFoundException {

		ScreenRegion targetRegion = findImageElement(fileName);
		Mouse mouse = new DesktopMouse();
		mouse.click(targetRegion.getCenter());

	}

	public void doubleClick(String fileName) throws SikuliRuntimeException, FileNotFoundException {

		ScreenRegion targetRegion = findImageElement(fileName);
		Mouse mouse = new DesktopMouse();
		mouse.doubleClick(targetRegion.getCenter());

	}

	public void mouseOver(String fileName) throws SikuliRuntimeException, FileNotFoundException {

		ScreenRegion targetRegion = findImageElement(fileName);
		Mouse mouse = new DesktopMouse();

		mouse.drop(targetRegion.getCenter());

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

	public ScreenRegion getRelativeImage(String fileName, int xOffset, int yOffset, int w, int h)
			throws SikuliRuntimeException, FileNotFoundException {

		ScreenRegion targetRegion = findImageElement(fileName);

		ScreenRegion relativeImage = targetRegion.getRelativeScreenRegion(xOffset, yOffset, w, h);

		return relativeImage;

	}

	public void clickAt(String fileName, int x, int y) throws SikuliRuntimeException, FileNotFoundException {

		ScreenRegion targetRegion = findImageElement(fileName);

		Mouse mouse = new DesktopMouse();

		mouse.click(targetRegion.getRelativeScreenLocation(x, y));

	}

	public void closeApp() throws IOException {
		String[] cmd = { "/usr/bin/osascript", "-e quit app \"" + openAppName + "\"'" };
		Runtime.getRuntime().exec(cmd);
	}

}
