import React from 'react';
import {Carousel} from "react-bootstrap";

const ImagesCarousel = ({images}) => {

    return (
        <Carousel interval={null} style={{maxWidth: "500px", maxHeight: "100%"}}>
            {images.map((image, index) => (
                <Carousel.Item key={index} style={{maxWidth: "100%", maxHeight: "100%"}}>
                    <img
                        className="d-block w-100 img-fluid rounded"
                        src={image}
                        alt={`Slide ${index + 1}`}
                        style={{ width: "100%", height: "100%" }}
                    />
                </Carousel.Item>
            ))}
        </Carousel>
    );
}

export default ImagesCarousel;