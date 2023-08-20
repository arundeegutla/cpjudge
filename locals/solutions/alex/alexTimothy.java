import java.util.*;
import java.io.*;

/*

2
38.946474 -77.437568
39.918620 116.443983

28.430849 -81.324178
51.507370 -0.148300


*/


public class alexTimothy {

	static double RADIUS = 6371;
	static double eps = 1e-6;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		// Read in number of test cases.
		int T = in.nextInt();
		
		Vec northPole = getPointOnEarth(90, 0);
		Vec center = new Vec(0, 0, 0);
		
		for (int tripI = 1; tripI <= T; tripI++) {
			// Read in the coordinates of the departure city.
			double lat0 = in.nextDouble();
			double lon0 = in.nextDouble();

			// Read in the coordinates of the destination  city.
			double lat1 = in.nextDouble();
			double lon1 = in.nextDouble();
			
			validateInput(tripI, lat0, lon0, lat1, lon1);
			
			// Get the 3D points for the departure and destination cities.
			Vec p0 = getPointOnEarth(lat0, lon0);
			Vec p1 = getPointOnEarth(lat1, lon1);
			
			if (p0.sub(p1).mag() < eps) {
				exitWithDataValidationError("Case " + tripI + ": Departure and destination cities too close!");
			}
			if (p0.sub(northPole).mag() < eps) {
				exitWithDataValidationError("Case " + tripI + ": Departure city too close to North Pole!");
			}
			if (p1.sub(northPole).mag() < eps) {
				exitWithDataValidationError("Case " + tripI + ": Destination city too close to North Pole!");
			}
			if (p0.sub(center).cross(p1.sub(center)).mag() < eps) {
				exitWithDataValidationError("Case " + tripI + ": Only one shortest path should exist!");
			}
			
			// Initialize the answer (minimum distance) as the min of the
			// distance from the Noth Pole to both the departure and destination cities.
			double ans = Math.min(p0.sub(northPole).mag(), p1.sub(northPole).mag());
			
			// Project the north pole onto the plane defined by the center and the
			// two cities.
			Vec proj = Vec.proj(center, p0, p1, northPole);

			// Start by assuming that Timothy is right.
			String personRight = "Timothy";
			
			Vec projXY = moveToXYPlane(center, p0, p1, proj);
			Vec northPoleXY = moveToXYPlane(center, p0, p1, northPole);
			Vec p0XY = moveToXYPlane(center, p0, p1, p0);
			Vec p1XY = moveToXYPlane(center, p0, p1, p1);
			Vec centerXY = moveToXYPlane(center, p0, p1, center);
			
			Vec ray = projXY.sub(centerXY);
			
			// If the flight path is not just along the equator,
			// there is a chance that Alex is right.
			if (ray.mag2() > eps) {
				// Calculate the minimum distance from the North Pole to a point on the
				// great circle defined by the two cities.
				double dxy = RADIUS - proj.sub(center).mag();
				double dz2 = northPole.sub(proj).mag2();
				double dist = Math.sqrt(dxy * dxy + dz2);
				
				Vec ray0 = p0XY.sub(centerXY);
				Vec ray1 = p1XY.sub(centerXY);
				double cross0 = ray0.cross(ray).z;
				double cross1 = ray.cross(ray1).z;
				double cross01 = ray0.cross(ray1).z;
				
				int s0 = (int) Math.signum(cross0);
				int s1 = (int) Math.signum(cross1);
				int s01 = (int) Math.signum(cross01);
				
				if ((s0 == s01 && s1 == s01) || Math.abs(cross0) < 1e-6 || Math.abs(cross1) < 1e-6) {
					personRight = "Alex";
					if (Math.abs(ans - dist) < 1.001) {
						exitWithDataValidationError("Case " + tripI + ": Answer too close");
					}
					ans = dist;
				}
			}
			
			System.out.println(personRight);
			System.out.printf("%.6f\n\n", ans);
		}
	}
	
	// Given 3 non-colinear points that define a plane and a point on that plane,
	// Project that point to the XY-plane retaining relative positioning and scale on the plane.
	static Vec moveToXYPlane(Vec a, Vec b, Vec c, Vec p) {
		Vec vx = b.sub(a).normalize();
		Vec vy = c.sub(a).sub(vx.scale(vx.dot(c.sub(a)))).normalize();
		return new Vec(vx.dot(p), vy.dot(p), 0);
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
	
	static void validateInput(int tripI, double lat0, double lon0, double lat1, double lon1) {
		if (lat0 < -90 || lat0 > 90) {
			exitWithDataValidationError("Case " + tripI + ": Departure city latitutde " + lat0 + " out of range!");
		}
		if (lat1 < -90 || lat1 > 90) {
			exitWithDataValidationError("Case " + tripI + ": Destination city latitutde " + lat1 + " out of range!");
		}
		if (lon0 < -180 || lon0 > 180) {
			exitWithDataValidationError("Case " + tripI + ": Departure city longitude " + lon0 + " out of range!");
		}
		if (lon1 < -180 || lon1 > 180) {
			exitWithDataValidationError("Case " + tripI + ": Destination city longitude " + lon1 + " out of range!");
		}
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
		
		// Renters the minimum angle between this vector and the vector v.
		public double angleBetween(Vec v) {
			return Math.acos( Math.max(-1, Math.min(1, dot(v) / (mag() * v.mag()))) );
		}
		
		// Given a plane defined by a, b, and c, returns the projection of p onto the plane.
		public static Vec proj(Vec a, Vec b, Vec c, Vec p) {
			Vec ab = b.sub(a);
			Vec ac = c.sub(a);
			Vec norm = ab.cross(ac).scaleTo(1);
			return p.sub(norm.scale(p.sub(a).dot(norm)));
		}
		
		public String toString() {
			return String.format("<%.3f, %.3f, %.3f>", x, y, z);
		}
	}
}