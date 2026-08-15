<script lang='ts'>
  let {data} = $props();
</script>

<h1>Welcome, user {data.id} </h1>
<p>
{#if data.id == null}
<a href="/login"> login</a>
{:else}
<a href="/change-password"> change password</a>
<a href="/logout"> logout</a>
{/if}
{#if data.id == 0}
<a href="/admin"> admin</a>
{/if}
<a href="/help">help</a>
</p>
