async function uploadImages(images) {

    const cloudinaryCloudName = "dhj64eq7m";
    const url = `https://api.cloudinary.com/v1_1/${cloudinaryCloudName}/upload`;
    const formDataArray = [];


    for (const image of images) {
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


    return results;
}
export default uploadImages;