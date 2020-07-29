import React from "react"
import "./global-loader.scss"

export const GlobalLoader: React.FC<{ enabled: boolean }> = ({ enabled  }) => {
  return enabled ? (
    <div className="progress" data-testid="global-loader">
      <div className="indeterminate" />
    </div>
  ) : null
}
