import React from "react"
import { ServiceRepository } from "../../services/services";

export const ServicesContext = React.createContext<ServiceRepository | null>(null);
