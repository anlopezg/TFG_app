import {useSelector, useDispatch} from 'react-redux';
import {useNavigate} from 'react-router-dom';

import * as selectors from '../selectors';
import * as actions from "../../users/actions.js";

import {FormattedMessage} from "react-intl";
import {Link} from "react-router-dom";
import {useState} from "react";
import {Errors} from "../../common/index.js";
import errors from "../../common/components/Errors.jsx";



const BecomesSeller = () => {

    const isUser = useSelector(selectors.isUser);
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const [backendErrors, setBackendErrors] = useState(null);

    let form;

    const handleBecomeSeller = (event) => {

        event.preventDefault();

        dispatch(actions.userBecomesSeller(
            navigate('/users/view-profile'),
            errors => setBackendErrors(errors)
            )
        );
    };

    return(
        <div>
            <Errors errors={backendErrors} onClose={() => setBackendErrors(null)}/>

            {isUser &&
            <div className="container-alert container-fluid pt-4">

                <div className="card bg-light mx-auto w-75">
                    <h2 className="retro card-header back-color-blue">
                        <FormattedMessage id="project.users.BecomeSeller.title"/>
                    </h2>
                    <div className="card-body p-5 ">
                        <form ref={node => form = node}
                                 className="needs-validation" noValidate onSubmit={e => handleBecomeSeller(e)}>
                            <div className="row justify-content-center">
                                <h4>
                                    <FormattedMessage id="project.users.BecomeSeller.intro"/>
                                </h4>
                            </div>

                            <div className="row justify-content-center mt-2">
                                <p className="text-center text-muted italic-message">
                                    <FormattedMessage id="project.users.BecomeSeller.account"/>
                                </p>
                            </div>

                            <div className="mt-4 container-button-row">
                                <div className="container-button-area">
                                    <Link className="btn button-options" to="/" style={{ textTransform: 'uppercase' }}>
                                        <FormattedMessage id="project.global.buttons.cancel" />
                                    </Link>
                                </div>
                                <div className="container-button-area">
                                    <button onClick={handleBecomeSeller} className="btn button-options extra-bold-label" style={{ textTransform: 'uppercase' }}>
                                        <FormattedMessage id="project.global.buttons.ok" />
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
            }
        </div>

    )

}




export default BecomesSeller;