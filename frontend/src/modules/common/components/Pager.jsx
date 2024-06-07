import PropTypes from 'prop-types';

const Pager = ({back, next}) => (

    <nav aria-label="page navigation">
        <ul className="pagination justify-content-center">
            <li className={`page-item ${back.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={back.onClick}>
                    <div className="d-flex align-items-center">
                        <i className="fa-regular fa-arrow-left-long arrow-lime"></i>

                    </div>
                </button>
            </li>
            <li className={`page-item ${next.enabled ? "": "disabled"}`}>
                <button className="page-link"
                    onClick={next.onClick}>

                    <div className="d-flex align-items-center">
                        <i className="fa-regular fa-arrow-right-long arrow-lime"></i>
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
