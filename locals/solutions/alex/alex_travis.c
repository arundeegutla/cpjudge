
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

#define PI    3.1415926535897932384626433832795
#define RAD   (6371.0)
#define EPS   (1e-9)

typedef struct Point {
   double x, y, z;
} Point;

int       run();
Point *   readInPoint();
Point *   makePointLongLat(double, double);
Point *   cross(Point *, Point *);
double    mag(Point *);
double    dist(Point *, Point *);
double    dot(Point *, Point *);
void      negate(Point *);
void      scale(Point *, double);
void      print(Point *);
double    angBetween(Point *, Point *);


int main(int argc, char ** argv)
{
   int n;
   
   scanf("%d", &n);
   
   while (n-->0)
   {
      if (run() == EXIT_FAILURE)
      {
         return EXIT_FAILURE;
      }
      if (n != 0)
      {
         printf("\n");
      }
   }
   
   return EXIT_SUCCESS;
}
    
int run()
{
   Point *   st;
   Point *   en;
   Point *   northPole;
   Point *   axis1;
   Point *   orthoPlane;
   Point *   opt;
   double    dotST;
   double    dotEN;
   double    smDist;
   double    tmp;
   double    ang1;
   double    ang2;
   
   st = readInPoint();
   en = readInPoint();
   
   northPole = makePointLongLat(0, 90);
   
   orthoPlane = cross(st, en);
   axis1 = cross(orthoPlane, northPole);
   
   // Check if on equator
   if (mag(axis1) < EPS)
   {
      printf("Timothy\n");
      printf("%lf\n", dist(northPole, st) * RAD);
      
      // Clean
      free(st);
      free(en);
      free(northPole);
      free(axis1);
      free(orthoPlane);
      
      // return
      return EXIT_SUCCESS;
   }
   
   // Get the gradient of the start and end points
   dotST = dot(axis1, st);
   dotEN = dot(axis1, en);
   
   // Check if the desired plane splits the path
   if ((dotST > EPS && dotEN < -EPS) || (dotST < -EPS && dotEN > EPS))
   {
      // Get the direction of the optimal point
      opt = cross(axis1, orthoPlane);
      scale(opt, 1.0 / mag(opt)); // Ensure on surface of sphere
      smDist = dist(opt, northPole);
      
      // Check if optimal point was on the opposite side of the world...
      negate(opt);
      tmp = dist(opt, northPole);
      
      // Take the smaller distance
      if (tmp < smDist)
      {
          smDist = tmp;
      }
      else
      {
          // The other distance was smaller
          negate(opt);
      }
      
      // Get the angles
      ang1 = angBetween(opt, st);
      ang2 = angBetween(opt, en);
      
      // Check if the optimal point was on the wrong side of the globe
      if (ang1 + ang2 > PI - EPS)
      {
          free(opt);
          opt = NULL;
      }
   }
   else
   {
       opt = NULL;
   }
   
   
   if (opt != NULL)
   {
      // Print that Alex was correct
      printf("Alex\n");
      printf("%lf\n", smDist * RAD);
      
      // Free this extra point
      free(opt);
   }
   else
   {
      // Get distance between the start and end to northPole
      smDist = dist(northPole, st);
      tmp = dist(northPole, en);
      
      // Get the smallest distance
      if (tmp < smDist)
      {
          smDist = tmp;
      }
      
      // Print out that Tim was correct
      printf("Timothy\n");
      printf("%lf\n", smDist * RAD);
   }
   
   // clean
   free(st);
   free(en);
   free(northPole);
   free(axis1);
   free(orthoPlane);
   
   // Return
   return EXIT_SUCCESS;
}


Point * readInPoint()
{
   double lat;
   double lon;
   
   // Read
   scanf("%lf", &lat);
   scanf("%lf", &lon);
   
   // Make and return
   return makePointLongLat(lon, lat);
}


Point * makePointLongLat(double lon, double lat)
{ 
   Point *   ret;
   
   // Convert to radians
   lon = (lon * PI) / 180.0;
   lat = (lat * PI) / 180.0;
   
   // Allocate
   ret = (Point *) calloc(1, sizeof(Point));
   
   // Do math
   ret->z = 1.0      * sin(lat);
   ret->y = cos(lon) * cos(lat);
   ret->x = sin(lon) * cos(lat);
   
   // Return
   return ret;
}


Point * cross(Point * f, Point * s)
{
   Point * ret;
   
   // Allocte
   ret = (Point *) calloc(1, sizeof(Point));
   
   // Do the math for a cross product
   ret->x = (f->y * s->z) - (f->z * s->y);
   ret->y = (f->z * s->x) - (f->x * s->z);
   ret->z = (f->x * s->y) - (f->y * s->x);
   
   // Return
   return ret;
}


double mag(Point * f)
{
   return sqrt((f->x * f->x) + (f->y * f->y) + (f->z * f->z));
}


double dist(Point * f, Point * s)
{
    double x = f->x - s->x;
    double y = f->y - s->y;
    double z = f->z - s->z;

    return sqrt(x * x + y * y + z * z);
}

double dot(Point * f, Point * s)
{
   return (f->x * s->x) + (f->y * s->y) + (f->z * s->z);
}


void negate(Point * f)
{
    f->x = -f->x;
    f->y = -f->y;
    f->z = -f->z;
}


void print(Point * f)
{
   printf("( %.03lf %.03lf %.03lf )\n", f->x, f->y, f->z);
}


void scale(Point * f, double a)
{
   f->x *= a;
   f->y *= a;
   f->z *= a;
}


double angBetween(Point * f, Point * s)
{
    // Formula for angle between is the inverse cosine of the 
    // normalized dot product
    return acos(dot(f, s) / (mag(f) * mag(s)));
}
