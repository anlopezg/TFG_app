// useImageUploader.js
import { useState } from 'react';

const imageUploader = () => {
    const [images, setImages] = useState([]);
    const [previewUrls, setPreviewUrls] = useState([]);

    const handleImagesChange = e => {
        const selectedFiles = Array.from(e.target.files);
        setImages(selectedFiles);
        const newPreviewUrls = selectedFiles.map(file => URL.createObjectURL(file));
        setPreviewUrls(newPreviewUrls);
    };

    return { images, previewUrls, handleImagesChange };
};

export default imageUploader;
