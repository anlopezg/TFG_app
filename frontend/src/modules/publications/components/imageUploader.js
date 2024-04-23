// useImageUploader.js
import { useState } from 'react';
import deleteImages from "../../../backend/cloudinary/deleteImages.js";

const imageUploader = () => {
    const [images, setImages] = useState([null, null, null, null, null]);
    const [previewUrls, setPreviewUrls] = useState([null, null, null, null, null]);

    const handleImagesChange = (file, index) => {
        if(file){
            const newImages = [...images];
            const newPreviewUrls = [...previewUrls];

            newImages[index] = file;
            newPreviewUrls[index] = URL.createObjectURL(file);

            setImages(newImages);
            setPreviewUrls(newPreviewUrls);
        }

        /*const selectedFiles = Array.from(e.target.files).slice(0,5);
        setImages(selectedFiles);
        const newPreviewUrls = selectedFiles.map(file => URL.createObjectURL(file));
        setPreviewUrls(newPreviewUrls);*/
    };

    const handleDeleteImage = (index) => {

        if (previewUrls[index]) URL.revokeObjectURL(previewUrls[index]);

        const newImages = [...images];
        const newPreviewUrls = [...previewUrls];

        newImages[index] = null;
        newPreviewUrls[index] = null;

        setImages(newImages);
        setPreviewUrls(newPreviewUrls);
    };


    return { images, previewUrls, handleImagesChange, handleDeleteImage };
};

export default imageUploader;
