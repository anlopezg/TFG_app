async function deleteImages(urls) {

    const extractIdFromUrl = (url) => {
        const idRegex = /\/([^/]+)\.jpg$/;
        const match = url.match(idRegex);
        return match ? match[1] : null;
    };

    const publicIdsParam = urls.map((url)=>extractIdFromUrl(url));
    const response = await fetch(`/api/deleteImages?publicIds=${publicIdsParam}`);
    const data = await response.json();
    return data;

}
export default deleteImages;