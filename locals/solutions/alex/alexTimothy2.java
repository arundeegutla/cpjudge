import java.util.*;

public class alexTimothy2 {
	
	static double RADIUS = 6371;
	static double EPS = 1e-6;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// Read in number of trips.
		int n = in.nextInt();

		// Get the points for the northPole and the origin.
		Vec northPole = getPointOnEarth(90, 0);
		Vec origin = new Vec(0, 0, 0);
		for (int tripI = 0; tripI < n; tripI++) {
			double lat0 = in.nextDouble(), lon0 = in.nextDouble();
			double lat1 = in.nextDouble(), lon1 = in.nextDouble();
			
			boolean onEquator = Math.abs(lat0) < 1e-9 && Math.abs(lat1) < 1e-9;
			
			// Get the points of the two cities.
			Vec p0 = getPointOnEarth(lat0, lon0);
			Vec p1 = getPointOnEarth(lat1, lon1);

			// Calculate the initial minimum distance.
			double minDist = Math.min(p0.sub(northPole).mag(), p1.sub(northPole).mag());
			
			// Get a vector perpendicular to the plane defined by the origin and the two cities.
			Vec norm = p0.cross(p1);
			// Project the north pole on to the plane defined by the origin and the two cities.
			Vec proj = Vec.proj(origin, p0, p1, northPole);
			
			// Get the sign of the cross product (on the plane) from the projected north pole to both cities.
			int sig0 = sig(proj.cross(p0).dot(norm));
			int sig1 = sig(proj.cross(p1).dot(norm));

			// If the projected point is between the smallest arc formed by the two, Alex is right.
			if (!onEquator && sig0 == -1 && sig1 == 1) {
				double originalMinDist = minDist;
				// Get the true minimum distance.
				minDist = northPole.sub(proj.scaleTo(RADIUS)).mag();
				if (Math.abs(originalMinDist - minDist) < 1.001) {
					exitWithDataValidationError("Case " + tripI + ": When Alex is right, the distance must differ by at least 1!");
				}
				System.out.print("Alex\n");
			} else {
				System.out.print("Timothy\n");
			}
			// Print out the minimum distance.
			System.out.printf("%.6f\n\n", minDist);
		}
	}
	
	static int sig(double x) {
		if (Math.abs(x) < EPS)
			return 0;
		return x < 0 ? -1 : 1;
	}
	
	// Given the latitude and longitude (in degrees), returns the 3d point
	// on the sphere of radius 6371km centered around the origin.
	static Vec getPointOnEarth(double lat, double lon) {
		lat = lat * Math.PI / 180;
		lon = lon * Math.PI / 180;
		double x = Math.cos(lat) * Math.cos(lon);
		double y = Math.cos(lat) * Math.sin(lon);
		double z = Math.sin(lat);
		return new Vec(x, y, z).scale(RADIUS);
	}
	
	static void exitWithDataValidationError(String errorMessage) {
		System.err.println("Data validation error:");
		System.err.println(errorMessage);
		System.exit(1);
	}

	static class Vec {
		double x, y, z;
		
		public Vec(double xx, double yy, double zz) {
			x = xx;
			y = yy;
			z = zz;
		}
		
		public Vec add(Vec v) {
			return new Vec(x + v.x, y + v.y, z + v.z);
		}
		
		public Vec sub(Vec v) {
			return new Vec(x - v.x, y - v.y, z - v.z);
		}
		
		public Vec scale(double s) {
			return new Vec(x * s, y * s, z * s);
		}
		
		public Vec scaleTo(double s) {
			return scale(s / mag());
		}
		
		public Vec normalize() {
			return scaleTo(1);
		}
		
		public Vec cross(Vec v) {
			return new Vec(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
		}

		public double dot(Vec v) {
			return x * v.x + y * v.y + z * v.z;
		}
		
		public double mag2() {
			return dot(this);
		}
		
		public double mag() {
			return Math.sqrt(mag2());
		}
		
		// Given a plane defined by a, b, and c, returns the projection of p onto the plane.
		public static Vec proj(Vec a, Vec b, Vec c, Vec p) {
			Vec ab = b.sub(a);
			Vec ac = c.sub(a);
			Vec norm = ab.cross(ac).normalize();
			return p.sub(norm.scale(p.sub(a).dot(norm)));
		}
		
		public String toString() {
			return String.format("<%.3f, %.3f, %.3f>", x, y, z);
		}
	}
}