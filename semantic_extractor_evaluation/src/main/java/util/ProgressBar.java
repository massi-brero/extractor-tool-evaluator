package util;

import java.text.DecimalFormat;

public class ProgressBar {
	
	/**
	 * The counter.
	 */
	private static int progressRatio = 0;
	
	/**
	 * 
	 * Shows a progress Bar for the console.
	 * 
	 * @param int totalLength
	 * @param int idx actual position in iteration; start with 0
	 * @param int steps 
	 */
	public void showProgress(int totalLength, int idx, int steps) {

		float progress = idx == totalLength - 1 ? 100.00f : ((float) idx / totalLength) * 100;
		int newProgressRatio = (int)progress / steps;

		if (newProgressRatio > progressRatio) {
			StringBuilder output = new StringBuilder();
			DecimalFormat df = new DecimalFormat("#.00");
			output.append("\r")
				  .append(df.format(progress))
				  .append("%\t")
				  .append("[");
			for (int i = 1; i <= steps; i++) {
				if (i <= newProgressRatio)
					output.append("*");
				else
					output.append(".");		
			}
			System.out.print(output.append("]"));
			
			if (idx == totalLength - 1)
				progressRatio = 0;
			
		}

		progressRatio = newProgressRatio;

	}
	
	public ProgressBar init() {
		
		progressRatio = 0;

		return this;
	}

}
