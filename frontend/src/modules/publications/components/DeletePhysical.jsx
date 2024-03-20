import {useDispatch, useSelector} from "react-redux";
import * as userSelectors from "../../users/selectors.js";
import {Link, useNavigate, useParams} from "react-router-dom";
import * as actions from "../actions.js";
import {FormattedMessage} from "react-intl";

const DeletePhysical = () => {

    const user = useSelector(userSelectors.getUser);
    const {id} = useParams();
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const handleSubmit = ()=>{
        dispatch(actions.deletePhysical(
            user.userName,
            id,
            ()=> navigate('/publications/products')
        ));
    }

    return(

        <div>

            <div className="container-alert container-fluid pt-4">

                <div className="card bg-light mx-auto w-75">
                    <h2 className="card-header back-color-blue">
                        <FormattedMessage id="project.products.Physical.delete.title"/>
                    </h2>
                    <div className="card-body p-5 ">
                        <div className="row justify-content-center">
                            <h4>
                                <FormattedMessage id="project.products.Physical.delete.msg"/>
                            </h4>
                        </div>
                        <div className="mt-5 container-button-row">
                            <div className="container-button-area">
                                <Link className="btn button-options" to="/publications/products" style={{ textTransform: 'uppercase' }}>
                                    <FormattedMessage id="project.global.buttons.cancel" />
                                </Link>
                            </div>
                            <div className="container-button-area">
                                <button onClick={() => handleSubmit()} type="submit" className="btn button-options extra-bold-label" style={{ textTransform: 'uppercase' }}>
                                    <FormattedMessage id="project.global.buttons.ok" />
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    )


}

export default DeletePhysical;