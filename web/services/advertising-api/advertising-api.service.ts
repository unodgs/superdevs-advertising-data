import Axios, { AxiosInstance } from "axios"
import { Campaign, DateSample, DataSource } from "../../models/advertising.model";

declare var env: any;

export class AdvertisingApiService {
  #serversApi: AxiosInstance

  constructor() {
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
  
  getDateSamples(campaignIds: number[]): Promise<DateSample[]> {
    return this.#serversApi
        .post<DateSample[]>(`/date-samples`, campaignIds)
        .then(res => res.data)
        .catch(() => [])
  }

}
