import { AdvertisingApiService } from "./advertising-api/advertising-api.service";

export type ServiceRepository = ReturnType<typeof createServices>;

export const createServices = () => {
    return {
        advertisingApiService: new AdvertisingApiService()
    }
}