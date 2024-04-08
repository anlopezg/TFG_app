import PropTypes from 'prop-types';
import {FormattedMessage} from 'react-intl';

const Pager = ({back, next}) => (

    <nav aria-label="page navigation">
        <ul className="pagination justify-content-center">
            <li className={`page-item ${back.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={back.onClick}>
                    <div className="d-flex align-items-center">
                        <i className="fa-solid fa-arrow-left-long arrow-lime mr-1"></i>
                        <FormattedMessage id='project.global.buttons.back'/>
                    </div>
                </button>
            </li>
            <li className={`page-item ${next.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={next.onClick}>

                    <div className="d-flex align-items-center">
                        <FormattedMessage id='project.global.buttons.next'/>
                        <i className="fa-solid fa-arrow-right-long arrow-lime ml-1"></i>
                    </div>
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
