import Axios from "axios";

export function getBackendApi() {
    return Axios.create({
        baseURL: ''
    })
}