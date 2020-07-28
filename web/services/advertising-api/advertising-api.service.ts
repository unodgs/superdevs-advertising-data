import Axios, { AxiosInstance } from "axios"
import { Campaign, DataSample, DataSource } from "../../models/advertising.model";

declare var env: any;

export class AdvertisingApiService {
  #serversApi: AxiosInstance

  constructor() {
    console.log("API", env.SERVER_API_URL);
    this.#serversApi = Axios.create({
      baseURL: env.SERVER_API_URL + "/advertising"
    })
  }

  getDataSources(): Promise<DataSource[]> {
    return this.#serversApi
      .get<DataSource[]>("/data-sources")
      .then(res => res.data)
      .catch(() => [])
  }

  getCampaigns(dataSourceIds: number[]): Promise<Campaign[]> {
    return this.#serversApi
      .get<Campaign[]>(`/campaigns?dataSourceIds=${dataSourceIds.join(',')}`)
      .then(res => res.data)
      .catch(() => [])
  }
  
  getDataSamples(campaignIds: number[]): Promise<DataSample[]> {
    return this.#serversApi
        .get<DataSample[]>(`/data-samples?campaignIds=${campaignIds.join(',')}`)
        .then(res => res.data)
        .catch(() => [])
  }

}
