async function uploadImages(images) {

    const cloudinaryCloudName = "dhj64eq7m";
    const uploadPreset = "rahixo6q";
    const url = `https://api.cloudinary.com/v1_1/${cloudinaryCloudName}/upload`;
    //const formDataArray = [];


    try {
        const uploadPromises = images
            .filter(image => image !== null)
            .map(async image => {
                const formData = new FormData();
                formData.append('file', image);
                formData.append('upload_preset', uploadPreset);

                const response = await fetch(url, {
                    method: 'POST',
                    body: formData
                });

                if (!response.ok) {
                    throw new Error(`Failed to upload image ${image.name}`);
                }

                return response.json();
            });

        return await Promise.all(uploadPromises);

    } catch (error) {
        console.error('Error uploading images:', error);
        throw error;
    }


    /*for (const image of images) {
        const formData = new FormData();
        formData.append('file', image);
        formData.append('upload_preset', 'rahixo6q');
        formDataArray.push(formData);
    }

    const responses = await Promise.all(
        formDataArray.map(formData =>
            fetch(url, {
                method: 'POST',
                body: formData
            })
        )
    );

    const results = await Promise.all(
        responses.map(response => response.json())
    );


    return results;*/
}
export default uploadImages;