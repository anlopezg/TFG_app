import PropTypes from 'prop-types';
import {FormattedMessage} from 'react-intl';

const Pager = ({back, next}) => (

    <nav aria-label="page navigation">
        <ul className="pagination justify-content-center">
            <li className={`page-item ${back.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={back.onClick}>
                    <i className="fa-solid fa-angle-left mr-2"></i>
                    <FormattedMessage id='project.global.buttons.back'/>
                </button>
            </li>
            <li className={`page-item ${next.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={next.onClick}>

                    <FormattedMessage id='project.global.buttons.next'/>
                    <i className="fa-solid fa-angle-right ml-1"></i>
                </button>
            </li>
        </ul>
    </nav>

);

Pager.propTypes = {
    back: PropTypes.object.isRequired,
    next: PropTypes.object.isRequired
};

export default Pager;
