# Dockerfile example

It demostrates difference between image and container.
If you restart container counter is incremented.
If you start new container from image, counter starts at 1 again.

Build image
    
    docker build -f Dockerfile -t test .

Check that image was created

    docker images

Run container from images and check that it was created

    docker run test
    docker ps -a

If you restart container  counter is incremented  
    
    docker start -a 8d2d157d48ca


If you start new container from image, counter starts at 1 again.
    
    docker run
