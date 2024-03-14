import {useNavigate} from 'react-router-dom';
import {FormattedMessage} from 'react-intl';

const BackLink = () => {

    const navigate = useNavigate();
    
    return (

        <button type="button" className="btn btn-link back-link-blue"
            onClick={() => navigate(-1)}>

            <i className="fa-solid fa-angle-left mr-2"></i>

            <FormattedMessage id='project.global.buttons.back'/>

        </button>

    );

};

export default BackLink;
