
import { useState } from 'react';
import deleteImages from "../../../backend/cloudinary/deleteImages.js";

const sectionImageUploader = () => {
    const [sectionImages, setSectionImages] = useState([null, null, null, null, null]);
    const [sectionPreviewUrls, setSectionPreviewUrls] = useState([null, null, null, null, null]);

    const handleSectionImagesChange = (file, index) => {
        if(file){
            const newSectionImages = [...sectionImages];
            const newSectionPreviewUrls = [...sectionPreviewUrls];

            newSectionImages[index] = file;
            newSectionPreviewUrls[index] = URL.createObjectURL(file);

            setSectionImages(newSectionImages);
            setSectionPreviewUrls(newSectionPreviewUrls);
        }

    };

    const handleDeleteSectionImage = (index) => {

        if (sectionPreviewUrls[index]) URL.revokeObjectURL(sectionPreviewUrls[index]);

        const newSectionImages = [...sectionImages];
        const newSectionPreviewUrls = [...sectionPreviewUrls];

        newSectionImages[index] = null;
        newSectionPreviewUrls[index] = null;

        setSectionImages(newSectionImages);
        setSectionPreviewUrls(newSectionPreviewUrls);
    };


    return { sectionImages, sectionPreviewUrls,  handleSectionImagesChange, handleDeleteSectionImage };
};

export default sectionImageUploader;
