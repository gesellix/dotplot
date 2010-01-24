package org.dotplot.image;

/**
 * This class contains several static methods that create on-the-fly look-up
 * tables.
 * 
 * @author Tobias Gesellchen
 */
public class LUTs {
	/**
	 * maximum number of steps.
	 */
	private final static int LUTSIZE = 256;

	/**
	 * List of available LUTs, used for the Colorbar canvas.
	 */
	public static final String[] availableLUTs = { "matrix4dotplot", "red",
			"green", "blue", "cyan", "magenta", "yellow", "gray",
			"inverted_gray", "two_levels", "four_levels", "eight_levels",
			"sixteen_levels", "thirty_two_levels", "sixty_four_levels",
			"hundred_twenty_eight_levels", "red_cyan", "green_magenta",
			"blue_yellow", "sin_rgb", "sin_rbg", "sin_grb", "sin_gbr",
			"sin_brg", "sin_bgr", "sin_rgb_0", "sin_rbg_0", "sin_grb_0",
			"sin_gbr_0", "sin_brg_0", "sin_bgr_0", "sqrt_rgb", "sqrt_rbg",
			"sqrt_grb", "sqrt_gbr", "sqrt_brg", "sqrt_bgr", "sqrt_rgb_0",
			"sqrt_rbg_0", "sqrt_brg_0", "sqrt_bgr_0", "sqrt_gbr_0",
			"sqrt_grb_0", "hue_rgb", "hue_rbg", "hue_grb", "hue_gbr",
			"hue_brg", "hue_bgr", "hue_rgb_0", "hue_rbg_0", "hue_grb_0",
			"hue_gbr_0", "hue_brg_0", "hue_bgr_0", "red_saw_2", "red_saw_4",
			"red_saw_8", "green_saw_2", "green_saw_4", "green_saw_8",
			"blue_saw_2", "blue_saw_4", "blue_saw_8", "red_green_saw_2",
			"red_green_saw_4", "red_green_saw_8", "red_blue_saw_2",
			"red_blue_saw_4", "red_blue_saw_8", "green_blue_saw_2",
			"green_blue_saw_4", "green_blue_saw_8", "random_256", "random_32",
			"random_8", };

	/**
	 * The blue lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] blue() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = 0;
			lut[1][i] = 0;
			lut[2][i] = i;
		}
		return lut;
	}

	/**
	 * The blue saw lut (number of teeth is passed as an argument).
	 * 
	 * @return a lut
	 */
	private final static int[][] blue_saw(int teeth) {
		int[][] lut = new int[3][LUTSIZE];
		int r = (LUTSIZE - 1);
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i;
			lut[1][i] = i;
			lut[2][i] = r;
			r -= teeth;
			if (r < 0) {
				r = (LUTSIZE - 1);
			}
		}
		return lut;
	}

	/**
	 * The blue saw lut (2 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] blue_saw_2() {
		return blue_saw((int) 2);
	}

	/**
	 * The blue saw lut (4 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] blue_saw_4() {
		return blue_saw((int) 4);
	}

	/**
	 * The blue saw lut (8 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] blue_saw_8() {
		return blue_saw((int) 8);
	}

	/**
	 * The blue-yellow lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] blue_yellow() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i;
			lut[1][i] = i;
			lut[2][i] = (int) (LUTSIZE - 1 - i);
		}
		return lut;
	}

	/**
	 * The cyan lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] cyan() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = 0;
			lut[1][i] = i;
			lut[2][i] = i;
		}
		return lut;
	}

	/**
	 * The eight-levels gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] eight_levels() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (32 * (i / 32));
			lut[1][i] = (32 * (i / 32));
			lut[2][i] = (32 * (i / 32));
		}
		return lut;
	}

	/**
	 * The four-levels gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] four_levels() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (64 * (i / 64));
			lut[1][i] = (64 * (i / 64));
			lut[2][i] = (64 * (i / 64));
		}
		return lut;
	}

	/**
	 * The gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] gray() {
		return gray_above(0.0);
		/*
		 * int[][] lut = new int[3][LUTSIZE]; for (int i = 0; i < LUTSIZE; i++)
		 * { lut[0][i] = i; lut[1][i] = i; lut[2][i] = i; } return lut;
		 */
	}

	/**
	 * The gray lut with selectable range (0.00 - 1.00). The resulting lut will
	 * start the grayscale (with "black") at the given percentage and the
	 * complete spectrum to "white" will be used.
	 * 
	 * @param percent
	 *            The range (in percent) from which the grayscale should be
	 *            built. Values have to be from 0.0 up to 1.0 (where "1.0"
	 *            doesn't make much sense...). "0.0" will produce the "complete"
	 *            grayscale.
	 * 
	 * @return a lut
	 */
	private final static int[][] gray_above(double percent) {
		int[][] lut = new int[3][LUTSIZE];
		double perc = ((percent > 0.0) ? Math.min(1.0, percent) : 1.0);
		double inv = 1.0 / ((perc < 1.0) ? (1.0 - perc) : 1.0);
		for (int i = (int) (LUTSIZE * percent); i < LUTSIZE; i++) {
			lut[0][i] = Math.max(0, (int) (inv * (i - (LUTSIZE * percent))));
			lut[1][i] = Math.max(0, (int) (inv * (i - (LUTSIZE * percent))));
			lut[2][i] = Math.max(0, (int) (inv * (i - (LUTSIZE * percent))));
		}

		return lut;
	}

	/**
	 * The green lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] green() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = 0;
			lut[1][i] = i;
			lut[2][i] = 0;
		}
		return lut;
	}

	/**
	 * The red-blue saw lut (number of teeth passed as argument).
	 * 
	 * @return a lut
	 */
	private final static int[][] green_blue_saw(int teeth) {
		int[][] lut = new int[3][LUTSIZE];
		int g = (LUTSIZE - 1);
		int b = 0;
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i;
			lut[1][i] = g;
			lut[2][i] = b;
			g -= teeth;
			if (g < 0) {
				g = (LUTSIZE - 1);
			}
			b += teeth;
			if (b > (LUTSIZE - 1)) {
				b = 0;
			}
		}
		return lut;
	}

	/**
	 * The green_blue saw lut (2 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] green_blue_saw_2() {
		return green_blue_saw((int) 2);
	}

	/**
	 * The green_blue saw lut (4 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] green_blue_saw_4() {
		return green_blue_saw((int) 4);
	}

	/**
	 * The green_blue saw lut (8 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] green_blue_saw_8() {
		return green_blue_saw((int) 8);
	}

	/**
	 * The green-magenta lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] green_magenta() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i;
			lut[1][i] = (int) (LUTSIZE - 1 - i);
			lut[2][i] = i;
		}
		return lut;
	}

	/**
	 * The green saw lut (number of teeth is passed as an argument).
	 * 
	 * @return a lut
	 */
	private final static int[][] green_saw(int teeth) {
		int[][] lut = new int[3][LUTSIZE];
		int r = (LUTSIZE - 1);
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i;
			lut[1][i] = r;
			lut[2][i] = i;
			r -= teeth;
			if (r < 0) {
				r = (LUTSIZE - 1);
			}
		}
		return lut;
	}

	/**
	 * The green saw lut (2 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] green_saw_2() {
		return green_saw((int) 2);
	}

	/**
	 * The green saw lut (4 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] green_saw_4() {
		return green_saw((int) 4);
	}

	/**
	 * The green saw lut (8 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] green_saw_8() {
		return green_saw((int) 8);
	}

	/**
	 * The hue lut (bgr order).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_bgr() {
		int[][] temp = hue_rgb();
		for (int i = 0; i < LUTSIZE; i++) {
			int t = temp[0][i];
			temp[0][i] = temp[2][i];
			temp[2][i] = t;
		}
		return temp;
	}

	/**
	 * The hue lut (bgr order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_bgr_0() {
		int[][] lut = hue_bgr();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The hue lut (brg order).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_brg() {
		int[][] temp = hue_rgb();
		for (int i = 0; i < LUTSIZE; i++) {
			int r = temp[0][i];
			int g = temp[1][i];
			int b = temp[2][i];
			temp[0][i] = g;
			temp[1][i] = b;
			temp[2][i] = r;
		}
		return temp;
	}

	/**
	 * The hue lut (brg order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_brg_0() {
		int[][] lut = hue_brg();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The hue lut (gbr order).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_gbr() {
		int[][] temp = hue_rgb();
		for (int i = 0; i < LUTSIZE; i++) {
			int r = temp[0][i];
			int g = temp[1][i];
			int b = temp[2][i];
			temp[0][i] = b;
			temp[1][i] = r;
			temp[2][i] = g;
		}
		return temp;
	}

	/**
	 * The hue lut (gbr order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_gbr_0() {
		int[][] lut = hue_gbr();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The hue lut (grb order).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_grb() {
		int[][] temp = hue_rgb();
		for (int i = 0; i < LUTSIZE; i++) {
			int t = temp[0][i];
			temp[0][i] = temp[1][0];
			temp[1][i] = t;
		}
		return temp;
	}

	/**
	 * The hue lut (grb order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_grb_0() {
		int[][] lut = hue_grb();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The hue lut (rbg order).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_rbg() {
		int[][] temp = hue_rgb();
		for (int i = 0; i < LUTSIZE; i++) {
			int t = temp[1][i];
			temp[1][i] = temp[2][i];
			temp[2][i] = t;
		}
		return temp;
	}

	/**
	 * The hue lut (rbg order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_rbg_0() {
		int[][] lut = hue_rbg();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The hue lut (rgb order).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_rgb() {
		int[][] lut = new int[3][LUTSIZE];
		float r = (LUTSIZE - 1), g = 0, b = 0;
		for (int i = 0; i < 43; i++) {
			lut[0][i] = (int) r;
			lut[1][i] = (int) g;
			lut[2][i] = (int) b;
			g += 5.9;
		}
		r = (LUTSIZE - 1);
		g = (LUTSIZE - 1);
		b = 0;
		for (int i = 43; i < 86; i++) {
			lut[0][i] = (int) r;
			lut[1][i] = (int) g;
			lut[2][i] = (int) b;
			r -= 5.9;
		}
		r = 0;
		g = (LUTSIZE - 1);
		b = 0;
		for (int i = 86; i < 128; i++) {
			lut[0][i] = (int) r;
			lut[1][i] = (int) g;
			lut[2][i] = (int) b;
			b += 5.9;
		}
		r = 0;
		g = (LUTSIZE - 1);
		b = (LUTSIZE - 1);
		for (int i = 128; i < 171; i++) {
			lut[0][i] = (int) r;
			lut[1][i] = (int) g;
			lut[2][i] = (int) b;
			g -= 5.9;
		}
		r = 0;
		g = 0;
		b = (LUTSIZE - 1);
		for (int i = 171; i < 213; i++) {
			lut[0][i] = (int) r;
			lut[1][i] = (int) g;
			lut[2][i] = (int) b;
			r += 5.9;
		}
		r = (LUTSIZE - 1);
		g = 0;
		b = (LUTSIZE - 1);
		for (int i = 213; i < LUTSIZE; i++) {
			lut[0][i] = (int) r;
			lut[1][i] = (int) g;
			lut[2][i] = (int) b;
			b -= 5.9;
		}
		return lut;
	}

	/**
	 * The hue lut (rgb order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] hue_rgb_0() {
		int[][] lut = hue_rgb();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The hundred-twenty-eight-levels gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] hundred_twenty_eight_levels() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) (2 * (int) (i / 2));
			lut[1][i] = (int) (2 * (int) (i / 2));
			lut[2][i] = (int) (2 * (int) (i / 2));
		}
		return lut;
	}

	/**
	 * The inverted gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] inverted_gray() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (LUTSIZE - 1 - i);
			lut[1][i] = (LUTSIZE - 1 - i);
			lut[2][i] = (LUTSIZE - 1 - i);
		}
		return lut;
	}

	/**
	 * The magenta lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] magenta() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i;
			lut[1][i] = 0;
			lut[2][i] = i;
		}
		return lut;
	}

	/**
	 * The default matrix4.plot lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] matrix4dotplot() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i / 6;
			lut[1][i] = i;
			lut[2][i] = i / 4;
		}
		lut[0][0] = 0;
		lut[1][0] = 0;
		lut[2][0] = 0;
		lut[0][LUTSIZE - 1] = LUTSIZE - 1;
		lut[1][LUTSIZE - 1] = LUTSIZE - 1;
		lut[2][LUTSIZE - 1] = LUTSIZE - 1;

		return lut;
	}

	/**
	 * The random_256 lut (values are totally independent).
	 * 
	 * @return a lut
	 */
	public final static int[][] random_256() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) (Math.random() * LUTSIZE);
			lut[1][i] = (int) (Math.random() * LUTSIZE);
			lut[2][i] = (int) (Math.random() * LUTSIZE);
		}
		return lut;
	}

	/**
	 * The random_32 lut (values are totally independent).
	 * 
	 * @return a lut
	 */
	public final static int[][] random_32() {
		int[][] lut = new int[3][LUTSIZE];
		int r = 0, g = 0, b = 0;
		for (int i = 0; i < LUTSIZE; i++) {
			if (i % 8 == 0) {
				r = (int) (Math.random() * LUTSIZE);
				g = (int) (Math.random() * LUTSIZE);
				b = (int) (Math.random() * LUTSIZE);
			}
			lut[0][i] = r;
			lut[1][i] = g;
			lut[2][i] = b;
		}
		return lut;
	}

	/**
	 * The random_8 lut (values are totally independent).
	 * 
	 * @return a lut
	 */
	public final static int[][] random_8() {
		int[][] lut = new int[3][LUTSIZE];
		int r = 0, g = 0, b = 0;
		for (int i = 0; i < LUTSIZE; i++) {
			if (i % 32 == 0) {
				r = (int) (Math.random() * LUTSIZE);
				g = (int) (Math.random() * LUTSIZE);
				b = (int) (Math.random() * LUTSIZE);
			}
			lut[0][i] = r;
			lut[1][i] = g;
			lut[2][i] = b;
		}
		return lut;
	}

	/**
	 * The red lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] red() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i;
			lut[1][i] = 0;
			lut[2][i] = 0;
		}
		return lut;
	}

	/**
	 * The red-blue saw lut (number of teeth passed as argument).
	 * 
	 * @return a lut
	 */
	private final static int[][] red_blue_saw(int teeth) {
		int[][] lut = new int[3][LUTSIZE];
		int r = (LUTSIZE - 1);
		int b = 0;
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = r;
			lut[1][i] = i;
			lut[2][i] = b;
			r -= teeth;
			if (r < 0) {
				r = (LUTSIZE - 1);
			}
			b += teeth;
			if (b > (LUTSIZE - 1)) {
				b = 0;
			}
		}
		return lut;
	}

	/**
	 * The red_blue saw lut (2 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_blue_saw_2() {
		return red_blue_saw((int) 2);
	}

	/**
	 * The red_blue saw lut (4 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_blue_saw_4() {
		return red_blue_saw((int) 4);
	}

	/**
	 * The red_blue saw lut (8 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_blue_saw_8() {
		return red_blue_saw((int) 8);
	}

	/**
	 * The red-cyan lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] red_cyan() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) (LUTSIZE - 1 - i);
			lut[1][i] = i;
			lut[2][i] = i;
		}
		return lut;
	}

	/**
	 * The red-green saw lut (number of teeth passed as argument).
	 * 
	 * @return a lut
	 */
	private final static int[][] red_green_saw(int teeth) {
		int[][] lut = new int[3][LUTSIZE];
		int r = (LUTSIZE - 1);
		int g = 0;
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = r;
			lut[1][i] = g;
			lut[2][i] = i;
			r -= teeth;
			if (r < 0) {
				r = (LUTSIZE - 1);
			}
			g += teeth;
			if (g > (LUTSIZE - 1)) {
				g = 0;
			}
		}
		return lut;
	}

	/**
	 * The red_green saw lut (2 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_green_saw_2() {
		return red_green_saw((int) 2);
	}

	/**
	 * The red_green saw lut (4 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_green_saw_4() {
		return red_green_saw((int) 4);
	}

	/**
	 * The red_green saw lut (8 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_green_saw_8() {
		return red_green_saw((int) 8);
	}

	/**
	 * The red saw lut (number of teeth is passed as an argument).
	 * 
	 * @return a lut
	 */
	private final static int[][] red_saw(int teeth) {
		int[][] lut = new int[3][LUTSIZE];
		int r = (LUTSIZE - 1);
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = r;
			lut[1][i] = i;
			lut[2][i] = i;
			r -= teeth;
			if (r < 0) {
				r = (LUTSIZE - 1);
			}
		}
		return lut;
	}

	/**
	 * The red saw lut (2 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_saw_2() {
		return red_saw((int) 2);
	}

	/**
	 * The red saw lut (4 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_saw_4() {
		return red_saw((int) 4);
	}

	/**
	 * The red saw lut (8 teeth).
	 * 
	 * @return a lut
	 */
	public final static int[][] red_saw_8() {
		return red_saw((int) 8);
	}

	/**
	 * The sin lut (bgr order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_bgr() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[2][i] = (int) (127 * (1 + Math.sin(Math.PI * (i - 127)
					/ (LUTSIZE - 1))));
			lut[1][i] = (int) (127 * (1 + Math.sin(Math.PI * (i)
					/ (LUTSIZE - 1))));
			lut[0][i] = (int) (127 * (1 + Math.sin(Math.PI * (i + 127)
					/ (LUTSIZE - 1))));
		}
		return lut;
	}

	/**
	 * The sin lut (bgr order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_bgr_0() {
		int[][] lut = sin_bgr();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sin lut (brg order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_brg() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[2][i] = (int) (127 * (1 + Math.sin(Math.PI * (i - 127)
					/ (LUTSIZE - 1))));
			lut[0][i] = (int) (127 * (1 + Math.sin(Math.PI * (i)
					/ (LUTSIZE - 1))));
			lut[1][i] = (int) (127 * (1 + Math.sin(Math.PI * (i + 127)
					/ (LUTSIZE - 1))));
		}
		return lut;
	}

	/**
	 * The sin lut (brg order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_brg_0() {
		int[][] lut = sin_brg();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sin lut (gbr order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_gbr() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[1][i] = (int) (127 * (1 + Math.sin(Math.PI * (i - 127)
					/ (LUTSIZE - 1))));
			lut[2][i] = (int) (127 * (1 + Math.sin(Math.PI * (i)
					/ (LUTSIZE - 1))));
			lut[0][i] = (int) (127 * (1 + Math.sin(Math.PI * (i + 127)
					/ (LUTSIZE - 1))));
		}
		return lut;
	}

	/**
	 * The sin lut (gbr order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_gbr_0() {
		int[][] lut = sin_gbr();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sin lut (grb order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_grb() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[1][i] = (int) (127 * (1 + Math.sin(Math.PI * (i - 127)
					/ (LUTSIZE - 1))));
			lut[0][i] = (int) (127 * (1 + Math.sin(Math.PI * (i)
					/ (LUTSIZE - 1))));
			lut[2][i] = (int) (127 * (1 + Math.sin(Math.PI * (i + 127)
					/ (LUTSIZE - 1))));
		}
		return lut;
	}

	/**
	 * The sin lut (grb order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_grb_0() {
		int[][] lut = sin_grb();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sin lut (rbg order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_rbg() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) (127 * (1 + Math.sin(Math.PI * (i - 127)
					/ (LUTSIZE - 1))));
			lut[2][i] = (int) (127 * (1 + Math.sin(Math.PI * (i)
					/ (LUTSIZE - 1))));
			lut[1][i] = (int) (127 * (1 + Math.sin(Math.PI * (i + 127)
					/ (LUTSIZE - 1))));
		}
		return lut;
	}

	/**
	 * The sin lut (rbg order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_rbg_0() {
		int[][] lut = sin_rbg();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sin lut (rgb order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_rgb() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) (127 * (1 + Math.sin(Math.PI * (i - 127)
					/ (LUTSIZE - 1))));
			lut[1][i] = (int) (127 * (1 + Math.sin(Math.PI * (i)
					/ (LUTSIZE - 1))));
			lut[2][i] = (int) (127 * (1 + Math.sin(Math.PI * (i + 127)
					/ (LUTSIZE - 1))));
		}
		return lut;
	}

	/**
	 * The sin lut (rgb order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sin_rgb_0() {
		int[][] lut = sin_rgb();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sixteen-levels gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] sixteen_levels() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) (16 * (int) (i / 16));
			lut[1][i] = (int) (16 * (int) (i / 16));
			lut[2][i] = (int) (16 * (int) (i / 16));
		}
		return lut;
	}

	/**
	 * The sixty-four-levels gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] sixty_four_levels() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) (4 * (int) (i / 4));
			lut[1][i] = (int) (4 * (int) (i / 4));
			lut[2][i] = (int) (4 * (int) (i / 4));
		}
		return lut;
	}

	/**
	 * The sqrt lut (bgr order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_bgr() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) ((LUTSIZE - 1) - 15 * Math.sqrt(i));
			lut[1][i] = i;
			lut[2][i] = (int) ((LUTSIZE - 1) - 15 * Math
					.sqrt((LUTSIZE - 1) - i));
		}
		return lut;
	}

	/**
	 * The sqrt lut (bgr order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_bgr_0() {
		int[][] lut = sqrt_bgr();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sqrt lut (brg order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_brg() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[2][i] = (int) ((LUTSIZE - 1) - 15 * Math.sqrt(i));
			lut[1][i] = i;
			lut[0][i] = (int) ((LUTSIZE - 1) - 15 * Math
					.sqrt((LUTSIZE - 1) - i));
		}
		return lut;
	}

	/**
	 * The sqrt lut (brg order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_brg_0() {
		int[][] lut = sqrt_brg();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sqrt lut (gbr order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_gbr() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[1][i] = (int) ((LUTSIZE - 1) - 15 * Math.sqrt(i));
			lut[2][i] = i;
			lut[0][i] = (int) ((LUTSIZE - 1) - 15 * Math
					.sqrt((LUTSIZE - 1) - i));
		}
		return lut;
	}

	/**
	 * The sqrt lut (gbr order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_gbr_0() {
		int[][] lut = sqrt_gbr();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sqrt lut (grb order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_grb() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[1][i] = (int) ((LUTSIZE - 1) - 15 * Math.sqrt(i));
			lut[0][i] = i;
			lut[2][i] = (int) ((LUTSIZE - 1) - 15 * Math
					.sqrt((LUTSIZE - 1) - i));
		}
		return lut;
	}

	/**
	 * The sqrt lut (grb order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_grb_0() {
		int[][] lut = sqrt_grb();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sqrt lut (rbg order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_rbg() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) ((LUTSIZE - 1) - 15 * Math.sqrt(i));
			lut[2][i] = i;
			lut[1][i] = (int) ((LUTSIZE - 1) - 15 * Math
					.sqrt((LUTSIZE - 1) - i));
		}
		return lut;
	}

	/**
	 * The sqrt lut (rbg order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_rbg_0() {
		int[][] lut = sqrt_rbg();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The sqrt lut (rgb order).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_rgb() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) ((LUTSIZE - 1) - 15 * Math.sqrt(i));
			lut[1][i] = i;
			lut[2][i] = (int) ((LUTSIZE - 1) - 15 * Math
					.sqrt((LUTSIZE - 1) - i));
		}
		return lut;
	}

	/**
	 * The sqrt lut (rgb order, black as zero).
	 * 
	 * @return a lut
	 */
	public final static int[][] sqrt_rgb_0() {
		int[][] lut = sqrt_rgb();
		lut[0][0] = lut[0][1] = lut[0][2] = 0;
		return lut;
	}

	/**
	 * The thyrty-two-levels gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] thirty_two_levels() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (int) (8 * (int) (i / 8));
			lut[1][i] = (int) (8 * (int) (i / 8));
			lut[2][i] = (int) (8 * (int) (i / 8));
		}
		return lut;
	}

	/**
	 * The two-levels gray lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] two_levels() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = (128 * (i / 128));
			lut[1][i] = (128 * (i / 128));
			lut[2][i] = (128 * (i / 128));
		}
		return lut;
	}

	/**
	 * The yellow lut.
	 * 
	 * @return a lut
	 */
	public final static int[][] yellow() {
		int[][] lut = new int[3][LUTSIZE];
		for (int i = 0; i < LUTSIZE; i++) {
			lut[0][i] = i;
			lut[1][i] = i;
			lut[2][i] = 0;
		}
		return lut;
	}
}
