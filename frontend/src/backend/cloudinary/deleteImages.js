import { deleteFromCloudinary } from '../appFetch';

async function deleteImages(urls) {

    const extractIdFromUrl = (url) => {

        if (typeof url !== 'string') {
            console.error('URL not valid:', url);
            return null;
        }

        const idRegex = /\/([^/]+)\.jpg$/;
        const match = url.match(idRegex);
        return match ? match[1] : null;
    };

    const publicIdsParam = urls.map((url)=>extractIdFromUrl(url));
    const queryString = `?publicIds=${publicIdsParam.join(',')}`;

    try {
        const response = await deleteFromCloudinary(`/api/deleteImages${queryString}`, {
            method: 'DELETE'
        });
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error deleting images:', error);
        throw error;
    }

    /*const response = await fetch(`/api/deleteImages?publicIds=${publicIdsParam}` , {
        method: 'DELETE'
    });


    const data = await response.json();
    return data;*/

}
export default deleteImages;