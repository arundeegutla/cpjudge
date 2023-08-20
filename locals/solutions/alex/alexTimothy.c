#include <math.h>
#include <stdio.h>

#define EPS 1e-6
#define RADIUS 6371
#define PI 3.14159265358979

typedef struct Vec {
	double x, y, z;
} Vec;

double min(double a, double b) {
	return a < b ? a : b;
}

Vec newVec(double x, double y, double z) {
	Vec v;
	v.x = x;
	v.y = y;
	v.z = z;
	return v;
}

Vec vecAdd(Vec a, Vec b) {
	return newVec(a.x + b.x, a.y + b.y, a.z + b.z);
}

Vec vecSub(Vec a, Vec b) {
	return newVec(a.x - b.x, a.y - b.y, a.z - b.z);
}

double vecDot(Vec a, Vec b) {
	return a.x * b.x + a.y * b.y + a.z * b.z;
}

double vecMag2(Vec v) {
	return vecDot(v, v);
}

double vecMag(Vec v) {
	return sqrt(vecMag2(v));
}

Vec vecScale(Vec v, double s) {
	return newVec(v.x * s, v.y * s, v.z * s);
}

Vec vecScaleTo(Vec v, double s) {
	return vecScale(v, s / vecMag(v));
}
		
Vec vecNormalize(Vec v) {
	return vecScaleTo(v, 1);
}

Vec vecCross(Vec a, Vec b) {
	return newVec(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
}

// Given a plane defined by a, b, and c, returns the projection of p onto the plane.
Vec vecProj(Vec a, Vec b, Vec c, Vec p) {
	Vec ab = vecSub(b, a);
	Vec ac = vecSub(c, a);
	Vec norm = vecNormalize(vecCross(ab, ac));
	return vecSub(p, vecScale(norm, vecDot(vecSub(p, a), norm)));
}

int sig(double x) {
	if (fabs(x) < EPS)
		return 0;
	return x < 0 ? -1 : 1;
}
	
// Given the latitude and longitude (in degrees), returns the 3d point
// on the sphere of radius 6371km centered around the origin.
Vec getPointOnEarth(double lat, double lon) {
	lat = lat * PI / 180;
	lon = lon * PI / 180;
	double x = cos(lat) * cos(lon);
	double y = cos(lat) * sin(lon);
	double z = sin(lat);
	return vecScale(newVec(x, y, z), RADIUS);
}
	
int main(void) {
	int n;
	// Read in number of trips.
	scanf("%d", &n);
	
	// Get the points for the northPole and the origin.
	Vec northPole = getPointOnEarth(90, 0);
	Vec origin = newVec(0, 0, 0);
	for (int tripI = 0; tripI < n; tripI++) {
		double lat0, lon0, lat1, lon1;
		scanf("%lf", &lat0);
		scanf("%lf", &lon0);
		scanf("%lf", &lat1);
		scanf("%lf", &lon1);

		int onEquator = fabs(lat0) < 1e-9 && fabs(lat1) < 1e-9;
		
		// Get the points of the two cities.
		Vec p0 = getPointOnEarth(lat0, lon0);
		Vec p1 = getPointOnEarth(lat1, lon1);

		// Calculate the initial minimum distance.
		double minDist = min(vecMag(vecSub(p0, northPole)), vecMag(vecSub(p1, northPole)));
		
		// Get a vector perpendicular to the plane defined by the origin and the two cities.
		Vec norm = vecCross(p0, p1);
		// Project the north pole on to the plane defined by the origin and the two cities.
		Vec proj = vecProj(origin, p0, p1, northPole);
		
		// Get the sign of the cross product (on the plane) from the projected north pole to both cities.
		int sig0 = sig(vecDot(vecCross(proj, p0), norm));
		int sig1 = sig(vecDot(vecCross(proj, p1), norm));

		// If the projected point is between the smallest arc formed by the two, Alex is right.
		if (!onEquator && sig0 == -1 && sig1 == 1) {
			double originalMinDist = minDist;
			// Get the true minimum distance.
			minDist = vecMag(vecSub(northPole, vecScaleTo(proj, RADIUS)));
			printf("Alex\n");
		} else {
			printf("Timothy\n");
		}
		// Print out the minimum distance.
		printf("%.6f\n\n", minDist);
	}
	return 0;
}
