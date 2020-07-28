import React from "react"
import { useIsFetching } from "react-query"
import "./global-loader.scss"

export const GlobalLoader = () => {
  const enabled = useIsFetching() > 0
  return enabled ? (
    <div className="progress">
      <div className="indeterminate" />
    </div>
  ) : null
}
