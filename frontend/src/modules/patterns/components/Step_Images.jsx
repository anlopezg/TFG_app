import {useEffect} from "react";
import imageUploader from "../../publications/components/imageUploader.js";

const Step_Images = () =>{

    const { images, previewUrls, handleImagesChange, handleDeleteImage } = imageUploader();

    useEffect(() => {
        return () => {
            previewUrls.forEach(url => {
                if (url) URL.revokeObjectURL(url);
            });
        };
    }, [previewUrls]);


    return(
        <div>
            <div className="container image-upload-container justify-content-center dashed-border">

                {previewUrls.map((url, index) => (
                    <div key={index} className="row text-center">
                        <div className="col">
                            <input
                                type="file"
                                accept="image/*"
                                onChange={(e) => handleImagesChange(e.target.files[0], index)}
                                style={{ display: 'none' }}
                                id={`file-input-${index}`}
                            />

                            <label htmlFor={`file-input-${index}`} className="btn button-light-pink-images">
                                {url ? <img src={url} alt="Preview"  />
                                    :
                                    <i className="fa-solid fa-plus" style={{color: "#fcfcfc",}}></i>}
                            </label>
                        </div>

                        <div key={index} className="row">
                            <div className="col">
                                {url && (<button type="button" onClick={() => handleDeleteImage(index)} className="btn btn-danger">
                                    <i className="fa-solid fa-trash"></i></button>)}
                            </div>
                        </div>

                    </div>
                ))}
            </div>
        </div>
    );

}

export default Step_Images;