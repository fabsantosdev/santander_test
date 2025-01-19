package com.fabsantosdev.pullhub.domain.usecase

import com.fabsantosdev.pullhub.domain.model.PullRequest
import com.fabsantosdev.pullhub.domain.model.PullRequestQuery

interface GetPullRequestsUseCase : BaseUseCase<PullRequestQuery, List<PullRequest>>