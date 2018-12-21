package se.lexicon.teresia.thegolfgame;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class TheGolfGame {

	public static final double gravity = 9.8;
	public static final double PI = Math.PI;
	public static final double minDistance = 100;
	public static final double maxDistance = 1000;

	// Double.MIN_VALUE is used instead of 0 because I want the min and max values
	// to be INCLUDED in the range
	// Double.MIN_VALUE is the closes value to 0 I can get (as far as I know)
	public static final double minVelocity = Double.MIN_VALUE;
	public static final double maxVelocity = 151;
	public static final double minAngle = Double.MIN_VALUE;
	public static final double maxAngle = 90 - Double.MIN_VALUE;

	public static final double maxNumberOfHits = 15;
	public static double[][] courseData;

	public static Random rng = new Random();
	private static Scanner scanner = new Scanner(System.in);

	public static void main(String[] args) {

		double goalDistance;
		double givenAngle;
		double givenVelocity;
		double hitLength;

		System.out.println("Welcome to The Golf Game");
		do {
			goalDistance = createNewHole(minDistance, maxDistance);
			while (goalDistance >= 0.1 && courseData.length < maxNumberOfHits) {
				System.out
						.println("The cup is " + goalDistance + " m away. Enter the angle and velocity of your swing");
				givenAngle = getInputAngle();
				givenVelocity = getInputVelocity();
				hitLength = calculateDistance(givenAngle, givenVelocity);
				System.out.println("Your ball flew " + Double.toString(hitLength) + " m");

				saveGamedata(goalDistance, givenAngle, givenVelocity, hitLength);
				goalDistance = round(Math.abs(goalDistance - hitLength), 2);
				// System.out.println("The new distance to the cup is " + goalDistance);

			}
			printGameData();
			if ( goalDistance < 0.1) System.out.println("You hit the hole!");
			else System.out.println("Leave or try from the beginning!");
			

		} while (continueOrNot());

	}

	private static void printGameData() {
		System.out.println("Game History");
		System.out.println("Distance\tAngle\tVelocity\tLength");

		int i = 0;
		for (double[] swingData : courseData) {

			System.out.println(
					(i + 1) + " : " + swingData[0] + "\t" + swingData[1] + "\t" + swingData[2] + "\t\t" + swingData[3]);
			i++;
		}
		System.out.println("Totally " + courseData.length + " number of hits");

	}

	private static boolean continueOrNot() {
		System.out.println("Play again (Y/N) ?");
		String input = scanner.nextLine();
		boolean returnValue = true;
		switch (input) {
		case "Y":
		case "y":
			returnValue = true;
			break;
		case "N":
		case "n":
			returnValue = false;
			break;
		default:
			System.out.println("I'll take that as a YES");
			returnValue = true;
			break;
		}
		return returnValue;
	}

	private static void saveGamedata(double goalDistance2, double givenAngle, double givenVelocity, double hitLength) {

		courseData = Arrays.copyOf(courseData, courseData.length + 1); // add one row for the new stroke

		double[][] hitData = new double[1][4];
		hitData[0][0] = goalDistance2;
		hitData[0][1] = givenAngle;
		hitData[0][2] = givenVelocity;
		hitData[0][3] = hitLength;

		System.arraycopy(hitData, 0, courseData, courseData.length - 1, hitData.length);// add hitData to courseData

	}

	private static double calculateDistance(double givenAngle, double givenVelocity) {
		double radians = (PI / 180) * givenAngle;

		double distance = Math.pow(givenVelocity, 2) / gravity * Math.sin(2 * radians);

		return round(distance, 2);
	}

	private static double getInputVelocity() {
		double inputVelocity;
		System.out.print("Velocity (in m/s): ");
		String input = scanner.nextLine();
		try {
			inputVelocity = Double.parseDouble(input);
			if (rangeCheck(inputVelocity, minVelocity, maxVelocity))
				return inputVelocity;
			else
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number or value out of bounds (must be > 0 and <" + maxVelocity + ") ");
			return getInputVelocity();
		}
	}

	private static boolean rangeCheck(double theValue, double minValue, double maxValue) {
		if (theValue >= minValue && theValue <= maxValue)
			return true;
		else
			return false;
	}

	private static double getInputAngle() {
		double inputAngle;

		System.out.print("Angle (in degrees): ");
		String input = scanner.nextLine();
		try {
			inputAngle = Double.parseDouble(input);
			if (rangeCheck(inputAngle, minAngle, maxAngle))
				return inputAngle;
			else
				throw new NumberFormatException();
		} catch (NumberFormatException e) {
			System.out.println("Not a valid number value out of bounds (must be > 0 and < " + maxAngle + ")");
			return getInputAngle();
		}
	}

	private static double createNewHole(double min, double max) {

		// delete old data if any
		courseData = new double[0][4];
		// Randomize length of the course
		double len = rng.nextDouble() * (max - min) + min;
		return round(len, 2);
	}

	private static double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		BigDecimal bd = new BigDecimal(Double.toString(value));
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

	public static double[][] expandArray(double[][] source) {
		return Arrays.copyOf(source, source.length + 1);
	}

}
